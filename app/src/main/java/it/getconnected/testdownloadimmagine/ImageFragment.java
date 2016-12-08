package it.getconnected.testdownloadimmagine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;

/**
 * Created by federico on 08/12/16.
 */

public class ImageFragment extends Fragment {

    private BlueDotView mImageView;

    public static Fragment newInstance() {
        return new ImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // prevent the screen going to sleep while app is on foreground
        getActivity().findViewById(android.R.id.content).setKeepScreenOn(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image_view, container, false);

        mImageView = new BlueDotView(getContext());
        ((FrameLayout) root.findViewById(R.id.bluedot)).addView(mImageView);

        mImageView.setImage(ImageSource.asset("squirrel.jpg"));

        return root;
    }


}
