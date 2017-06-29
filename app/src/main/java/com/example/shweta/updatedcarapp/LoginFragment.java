package com.example.shweta.updatedcarapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment{
    //taking hardcode data
    private final String un = "shweta";
    private final String pass = "mindfire";
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button mb = (Button) view.findViewById(R.id.button);

        //event listener for  button click
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching input entered by user i.e username and password
                String usr = ((EditText) view.findViewById(R.id.editText)).getText().toString();
                String passw = ((EditText) view.findViewById(R.id.editText2))
                        .getText().toString();

                //checking whether user name field is filled or empty.
                if (usr.matches("")) {
                    Toast.makeText(getActivity(), "username cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking whether the password id entered or not
                if (passw.matches("")) {
                    Toast.makeText(getActivity(), "Please enter the password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking user entered values with value saved previously
                if ((un.equalsIgnoreCase(usr)) && (pass.equalsIgnoreCase(passw))) {
                    //making object of intent to move to next activity
                    ((MainActivity) getActivity()).showFragment(new InfoFragment());
                } else {
                    //getting context object for Toast to print message for user
                    CharSequence text = "UserName or Password is incorrect!";
                    //setting duration for taost display
                    int duration = Toast.LENGTH_LONG;
                    //displaying message through toast
                    Toast.makeText(getActivity(), text, duration).show();
                }

            }
        });
        return view;
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
