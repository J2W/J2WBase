package j2w.team.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import j2w.team.common.log.L;
import j2w.team.mvp.J2WIView;

/**
 * Created by sky on 15/2/1.
 */
public abstract class J2WBaseFragment extends Fragment implements J2WIView {
    /**
     * TAG标记
     */
    public abstract String getTAG();

    /** 初始化视图 **/
    @Override public void initView() {
        L.tag(getTAG());
        L.i("Fragment-initView()");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        L.tag(getTAG());
        L.i("Fragment-onAttach()");
        initView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.tag(getTAG());
        L.i("Fragment-onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.tag(getTAG());
        L.i("Fragment-onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.tag(getTAG());
        L.i("Fragment-onActivityCreated()");

    }

    @Override
    public void onStart() {
        super.onStart();
        L.tag(getTAG());
        L.i("Fragment-onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.tag(getTAG());
        L.i("Fragment-onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        L.tag(getTAG());
        L.i("Fragment-onSaveInstanceState()");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.tag(getTAG());
        L.i("Fragment-onSaveInstanceState()");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.tag(getTAG());
        L.i("Fragment-onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.tag(getTAG());
        L.i("Fragment-onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.tag(getTAG());
        L.i("Fragment-onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.tag(getTAG());
        L.i("Fragment-onDetach()");
    }


}
