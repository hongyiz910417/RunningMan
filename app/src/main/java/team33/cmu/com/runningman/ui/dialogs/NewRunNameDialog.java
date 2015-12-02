package team33.cmu.com.runningman.ui.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import team33.cmu.com.runningman.R;

public class NewRunNameDialog extends DialogFragment {
    public interface NewRunNameListener {
        void onFinishNewRunNameDialog(String name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_run_name_dialog, container);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Give it a name");
        Button confirmBtn = (Button) view.findViewById(R.id.newRunNameConfirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                NewRunNameListener activity = (NewRunNameListener) getActivity();
                EditText input = (EditText)view.findViewById(R.id.newRunNameInput);
                String name = input.getText().toString();
                activity.onFinishNewRunNameDialog(name);
                NewRunNameDialog.this.dismiss();
            }
        });
        return view;
    }
}
