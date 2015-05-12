package com.mymovielib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mDesc;
    private String mPoster;
    private String mDirector;
    private String mDirectorImg;
    private String mCast1;
    private String mCast2;
    private String mCast3;
    private String mCast4;
    private String mCast1Img;
    private String mCast2Img;
    private String mCast3Img;
    private String mCast4Img;
    private String mParam2Img;
    private String mPath;
    TextView movieName;
    TextView movieDesc;
    Button btn;
    TextView movieDirector;
    TextView movieActors;
    TextView movieCast1;
    TextView movieCast2;
    TextView movieCast3;
    TextView movieCast4;
    ImageView moviePoster;
    ImageView movieCastOne;
    ImageView movieCastTWo;
    ImageView movieCastThree;
    ImageView movieCastFour;
    ImageView movieDirectorImg;
    private OnFragmentInteractionListener mListener;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieInfoFragment newInstance(String param1, String param2) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            mName = b.getString("mName", "CERF");
            mDesc = b.getString("mDesc", "CERF");
            mPoster = b.getString("mPoster", null);
            mCast1 = b.getString("mCast1", null);
            mCast2 = b.getString("mCast2", null);
            mCast3 = b.getString("mCast3", null);
            mCast4 = b.getString("mCast4", null);
            mDirector = b.getString("mDirector", null);
            mDirectorImg = b.getString("mDirectorImg", null);
            mCast1Img = b.getString("mCastImg1", null);
            mCast2Img = b.getString("mCastImg2", null);
            mCast3Img = b.getString("mCastImg3", null);
            mCast4Img = b.getString("mCastImg4", null);
            mPath = b.getString("mPath", null);

           // mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.fragment_movie_info, container, false);
        // Inflate the layout for this fragment
        movieName = (TextView) myInflatedView.findViewById(R.id.movie_name);
        movieDesc = (TextView) myInflatedView.findViewById(R.id.movie_desc);
        movieDirector = (TextView) myInflatedView.findViewById(R.id.movie_director);

        moviePoster = (ImageView) myInflatedView.findViewById(R.id.movie_poster);
        movieDirectorImg = (ImageView) myInflatedView.findViewById(R.id.movie_director_img);
        movieCast1 = (TextView) myInflatedView.findViewById(R.id.movie_cast1);
        movieCast2 = (TextView) myInflatedView.findViewById(R.id.movie_cast2);
        movieCast3 = (TextView) myInflatedView.findViewById(R.id.movie_cast3);
        movieCast4 = (TextView) myInflatedView.findViewById(R.id.movie_cast4);
        movieCastOne = (ImageView) myInflatedView.findViewById(R.id.movie_cast1_img);
        movieCastTWo = (ImageView) myInflatedView.findViewById(R.id.movie_cast2_img);
        movieCastThree = (ImageView) myInflatedView.findViewById(R.id.movie_cast3_img);
        movieCastFour = (ImageView) myInflatedView.findViewById(R.id.movie_cast4_img);
        btn = (Button) myInflatedView.findViewById(R.id.btn);

       movieName.setText(mName);
        movieDesc.setText(mDesc);
        movieDirector.setText(mDirector);
        movieCast1.setText(mCast1);
        movieCast2.setText(mCast2);
        movieCast3.setText(mCast3);
        movieCast4.setText(mCast4);
        if(mPoster!= null) {
          setImg(moviePoster, mPoster);
            setImg(movieDirectorImg, mDirectorImg);
            setImg(movieCastOne, mCast1Img);
            setImg(movieCastTWo, mCast2Img);
            setImg(movieCastThree, mCast3Img);
            setImg(movieCastFour,mCast4Img);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video = mPath.substring(0, mPath.length()-5);
                Intent tostart = new Intent(Intent.ACTION_VIEW);
                tostart.setDataAndType(Uri.parse(chk_path(video)), "video/*");
                startActivity(tostart);
            }
        });
btn.requestFocus();
        return myInflatedView;
    }
    public void setImg(ImageView img, String base64){
        byte[] decodedString = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // Picasso.with(mContext).load(decodedByte).into(holder.imageView);
       img.setImageBitmap(decodedByte);
    }
    public String chk_path(String filePath)
    {
//create array of extensions
        String[] ext=new String[]{".avi", ".mkv",".mpg"}; //You can add more as you require

//Iterate through array and check your path which extension with your path exists

        String path=null;
        for(int i=0;i<ext.length;i++)
        {
            File file = new File(filePath+ext[i]);
            if(file.exists())
            {
                //if it exists then combine the extension
                path=filePath+ext[i];
                break;
            }
        }
        return path;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume() {
        super.onResume();


    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
