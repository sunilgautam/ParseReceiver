package app.receiver.parse.parsereceiver;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class EditDataDialogFragment extends DialogFragment implements View.OnClickListener
{
    Button btnSave, btnCancel;
    TextView lblEditKey;
    EditText txtEditValue;
    String editKey, editValue;
    FragmentCommunicator communicator;

    public EditDataDialogFragment()
    {

    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.communicator = (FragmentCommunicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_edit_data, null);

        editKey = getArguments().getString("key");
        editValue = getArguments().getString("value");

        lblEditKey = (TextView) view.findViewById(R.id.lblEditKey);
        txtEditValue = (EditText) view.findViewById(R.id.txtEditValue);

        lblEditKey.setText(editKey);
        txtEditValue.setText(editValue);

        btnSave = (Button) view.findViewById(R.id.btnEditSave);
        btnCancel = (Button) view.findViewById(R.id.btnEditCancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        // Hide keyboard before
        InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        //check if no view has focus:
        View vw = this.getActivity().getCurrentFocus();
        if (vw != null)
        {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (v.getId() == R.id.btnEditSave)
        {
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put(this.editKey, txtEditValue.getText().toString());

            installation.saveInBackground(new SaveCallback()
            {

                @Override
                public void done(ParseException e)
                {
                    communicator.onActivityMessage("From dialog", null);
                    Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
        else
        {
            dismiss();
        }
    }
}