package com.example.shweta.updatedcarapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class InfoFragment extends Fragment {
    private SharedPreferences spref;   //declaring shared preference for saving data
    private SharedPreferences.Editor ed;
    private Button mEditButton;
    private Button mSaveButton;
    private EditText team_name;
    private EditText mCaptain;
    private EditText mCoach;
    private EditText mTestRank;
    private EditText mOdiRank;
    private EditText mT20Rank;
    private ImageView mImgView;
    private FloatingActionButton mActionButton;
    private LoginFragment.OnFragmentInteractionListener mListener;
    private static final int SELECT_PICTURE=100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        spref = getActivity().getSharedPreferences("mypref", 0);  //gettiing shared preference
        ed = spref.edit();                                               //making editor
        mEditButton = (Button) view.findViewById(R.id.edit);
        mSaveButton = (Button) view.findViewById(R.id.save);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.action);
        team_name = (EditText) view.findViewById(R.id.team);
        mCaptain = (EditText) view.findViewById(R.id.cap);
        mCoach = (EditText) view.findViewById(R.id.coach);
        mTestRank = (EditText) view.findViewById(R.id.tst);
        mOdiRank = (EditText) view.findViewById(R.id.odi);
        mT20Rank = (EditText) view.findViewById(R.id.T20);
        mImgView = (ImageView) view.findViewById(R.id.imageView);
        changeTintcolor();                          //for changing tint color

        try {
            showdata();                               //Function for displaying data
        } catch (Exception e) {
        }

        mEditButton.setOnClickListener(new View.OnClickListener() {        //listener for edit button click
            @Override
            public void onClick(View v) {
                team_name.setEnabled(true);                    //enabling text fields
                mCaptain.setEnabled(true);
                mCoach.setEnabled(true);
                mTestRank.setEnabled(true);
                mOdiRank.setEnabled(true);
                mT20Rank.setEnabled(true);
                team_name.getBackground().
                        setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mCaptain.getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mCoach.getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mTestRank.getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
               mOdiRank.getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mT20Rank.getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mEditButton.setVisibility(View.GONE);
                mSaveButton.setVisibility(View.VISIBLE);
                mActionButton.setVisibility(View.VISIBLE);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {          //listener for save button click
            @Override
            public void onClick(View v) {
                //saving data in shared preference
                ed.putString("team_name",team_name.getText().toString());
                ed.putString("Captain", mCaptain.getText().toString());
                ed.putString("Coach", mCoach.getText().toString());
                ed.putString("TestRank", mTestRank.getText().toString());
                ed.putString("OdiRank", mOdiRank.getText().toString());
                ed.putString("T20Rank", mT20Rank.getText().toString());
                //converting image in bitmap
                Bitmap bitImg = ((BitmapDrawable) mImgView.getDrawable()).getBitmap();
                // saving encoded image in shared preference
                ed.putString("imageP", encodeTobase64(bitImg));
                ed.commit();
                team_name.setEnabled(false);
                mCaptain.setEnabled(false);
                mCoach.setEnabled(false);
                mTestRank.setEnabled(false);
                mOdiRank.setEnabled(false);
                mT20Rank.setEnabled(false);
                mSaveButton.setVisibility(View.GONE);
                mEditButton.setVisibility(View.VISIBLE);
                mActionButton.setVisibility(View.GONE);
                changeTintcolor();
            }
        });

        mActionButton.setOnClickListener(new View.OnClickListener() {      //listener for image change (action)button click
            @Override
            public void onClick(View view) {
                openImg();                                      //function to upload image
            }
        });

        return view;
    }

    private void changeTintcolor() {
        team_name.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        mCaptain.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        mCoach.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        mTestRank.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        mOdiRank.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        mT20Rank.getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
    }

    private void showdata() {
        Log.d("check data",spref.getString("team_name",""));
        team_name.setText(spref.getString("team_name",""));
        mCaptain.setText(spref.getString("Captain",""));
        mCoach.setText(spref.getString("Coach",""));
        mTestRank.setText(spref.getString("TestRank",""));
        mOdiRank.setText(spref.getString("OdiRank",""));
        mT20Rank.setText(spref.getString("T20Rank",""));
        Bitmap bp= decodeBase64(spref.getString("imageP",""));
        mImgView.setImageBitmap(bp);
    }

    private static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }  //function to encode bitmap image

    private Bitmap decodeBase64(String imageP) {
        byte[] decodedByte = Base64.decode(imageP, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }            //function to decode image

    private void openImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    mImgView.setImageURI(selectedImageUri);
                }
            }
        }
    }       //for uploading image from device

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            mListener = (LoginFragment.OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        //void onFragmentInteraction(Uri uri);
    }
}
