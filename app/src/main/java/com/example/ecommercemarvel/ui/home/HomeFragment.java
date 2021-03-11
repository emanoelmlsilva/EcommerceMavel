package com.example.ecommercemarvel.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.adapter.ComicAdapter;
import com.example.ecommercemarvel.dagger.DaggerApplicationComponent;
import com.example.ecommercemarvel.dagger.MyApplication;
import com.example.ecommercemarvel.dagger.NetworkModule;
import com.example.ecommercemarvel.model.Comic;
import com.example.ecommercemarvel.model.ResponseComic;
import com.example.ecommercemarvel.service.MarvelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private List<Comic> comics = new ArrayList<>();

    private int limit = 10;

    @Inject
    public MarvelService marvelService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        MyApplication.applicationComponent.inject(this);

        recyclerView = root.findViewById(R.id.recyclerComics);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

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

    private void getComics(int newLimit){

        limit += newLimit;

        marvelService.getComics(limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ResponseComic>() {

            @Override
            public void onNext(@io.reactivex.annotations.NonNull ResponseComic responseComic) {
                List<Comic> generatedNewList = generateListWithComicRare(responseComic.getData().getResults());
                comicAdapter.updateList(generatedNewList);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

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

}