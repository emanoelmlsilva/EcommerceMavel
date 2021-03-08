package com.example.ecommercemarvel.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.adapter.ComicAdapter;
import com.example.ecommercemarvel.model.Comic;
import com.example.ecommercemarvel.model.ResponseComic;
import com.example.ecommercemarvel.service.MarvelService;
import com.example.ecommercemarvel.service.RetrofitMarvel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<Comic> comics = new ArrayList<>();
    private MarvelService marvelService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerComics);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RetrofitMarvel retrofitMarvel = new RetrofitMarvel();
        marvelService = retrofitMarvel.getMarvelService();

        comicAdapter = new ComicAdapter(comics, getContext());
        recyclerView.setAdapter(comicAdapter);

        getComics(10);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {

                    getComics(10);

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return root;
    }

    private void getComics(int limit){

        marvelService.getComics(limit).enqueue(new Callback<ResponseComic>() {
            @Override
            public void onResponse(Call<ResponseComic> call, Response<ResponseComic> response) {
                List<Comic> generatedNewList = generateListWithComicRare(response.body().getData().getResults());
                loadingComic(generatedNewList);
            }

            @Override
            public void onFailure(Call<ResponseComic> call, Throwable t) {
                System.out.println("FAILURE "+t.getMessage() + " " + t.getStackTrace().toString());
            }
        });
    }

    private List<Comic> generateListWithComicRare(List<Comic> comics){
        int quantRare = (int) (comics.size() * 0.12f);
        int contRare = 0;
        for(Comic comic : comics){
            boolean isRare = new Random().nextInt() % 2 == 0;
            if(contRare < quantRare && isRare){
                contRare++;
                comic.setRare(isRare);
            }else {
                comic.setRare(false);
            }
        }

        return comics;
    }

    private void loadingComic (List<Comic> comicList){
        disposables.add(obserbable(comicList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Comic>>() {
                    @Override
                    public void onComplete() {
                        Log.d("TAG", " onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", " onError : " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Comic> results) {
                        Log.d("TAG", " onError : " + results.size());
                        comicAdapter.updateList(results);
                    }
                }));
    }

    private Observable<List<Comic>> obserbable(List<Comic> comics){
        return Observable.defer(new Callable<ObservableSource<? extends List<Comic>>>() {
            @Override
            public ObservableSource<? extends List<Comic>> call() throws Exception {
                return Observable.just(comics);
            }
        });
    }
}