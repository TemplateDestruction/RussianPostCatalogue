package com.example.russianpostcatalogue.domain.spinerdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.russianpostcatalogue.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialog {
    ArrayList<String> items;
    Activity context;
    String dTitle, closeTitle = "Close";
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    ArrayAdapterWithContainsFilter adapter;
    int pos;
    int style;
    boolean cancellable = false;
    boolean showKeyboard = false;
    boolean offlineMode = true;
    boolean useContainsFilter = false;
    int titleColor, searchIconColor, searchTextColor, itemColor, itemDividerColor, closeColor;
    EditText searchBox;

    private void initColor(Context context) {
        this.titleColor = context.getResources().getColor(R.color.col_white);
        this.searchIconColor = context.getResources().getColor(R.color.col_white);
        this.searchTextColor = context.getResources().getColor(R.color.col_black);
        this.itemColor = context.getResources().getColor(R.color.col_white);
        this.closeColor = context.getResources().getColor(R.color.col_white);
        this.itemDividerColor = context.getResources().getColor(R.color.col_white);
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        initColor(context);
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        initColor(context);
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
//        AlertDialog.Builder adb = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MyDialogTheme));
        View v = context.getLayoutInflater().inflate(R.layout.dialog_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        ImageView searchIcon = (ImageView) v.findViewById(R.id.searchIcon);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        final RecyclerView recyclerView = v.findViewById(R.id.list);

        ColorDrawable sage = new ColorDrawable(itemDividerColor);
        sage.setAlpha(46);
//        recyclerView.setDivider(sage);
//        recyclerView.setDividerHeight(1);

        searchBox = (EditText) v.findViewById(R.id.searchBox);
        if (isShowKeyboard()) {
            showKeyboard(searchBox);
        }

        title.setTextColor(titleColor);
        rippleViewClose.setTextColor(closeColor);
        searchIcon.setColorFilter(searchIconColor);
        searchIcon.setAlpha(0.18f);


//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.items_view, items);
//        adapter = new ArrayAdapterWithContainsFilter(context, R.layout.items_view, items) {
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                 View view = super.getView(position, convertView, parent);
//                TextView text1=view.findViewById(R.id.text1);
//                text1.setTextColor(itemColor);
//                return view;
//            }
//        };
        adapter = new ArrayAdapterWithContainsFilter(context, items);

        recyclerView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialogColor)));
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;

        adapter.setListener(onSpinerItemClick);
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView t = (TextView) view.findViewById(R.id.text1);
//                for (int j = 0; j < items.size(); j++) {
//                    if (t.getText().toString().equalsIgnoreCase(items.get(j).toString())) {
//                        pos = j;
//                    }
//                }
//                onSpinerItemClick.onClick(t.getText().toString(), pos);
//                closeSpinerDialog();
//            }
//        });

        if (offlineMode) {
            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    adapter.getContainsFilter(searchBox.getText().toString());
                }
            });
        }

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSpinerDialog();
            }
        });
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }

    public void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    private void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    public void setItems(ArrayList<String> items) {
        this.items.clear();
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
//        System.out.println("this.items: " + this.items);
//        adapter.notifyDataSetInvalidated();
    }

    private boolean isCancellable() {
        return cancellable;
    }

    private boolean isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    private boolean isShowKeyboard() {
        return showKeyboard;
    }

    private boolean isUseContainsFilter() {
        return useContainsFilter;
    }


    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public void setUseContainsFilter(boolean useContainsFilter) {
        this.useContainsFilter = useContainsFilter;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setSearchIconColor(int searchIconColor) {
        this.searchIconColor = searchIconColor;
    }

    public void setSearchTextColor(int searchTextColor) {
        this.searchTextColor = searchTextColor;
    }

    public void setItemColor(int itemColor) {
        this.itemColor = itemColor;
    }

    public void setCloseColor(int closeColor) {
        this.closeColor = closeColor;
    }

    public void setItemDividerColor(int itemDividerColor) {
        this.itemDividerColor = itemDividerColor;
    }

    public EditText getSearchBox() {
        return searchBox;
    }
}

class ArrayAdapterWithContainsFilter extends RecyclerView.Adapter<ArrayAdapterWithContainsFilter.ArrayViewHolder> {

    private List<String> items = null;
    private ArrayList<String> arraylist;
    Context context;

    private OnSpinerItemClick listener;

    public void setListener(OnSpinerItemClick listener) {
        this.listener = listener;
    }

    public ArrayAdapterWithContainsFilter(Activity context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(items);
    }


    // Filter Class
    public void getContainsFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (String item : arraylist) {
                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
        System.out.println("items adapter: " + items);
        System.out.println("arraylist adapter: " + arraylist);
    }




    @NonNull
    @Override
    public ArrayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(context).inflate(R.layout.youtube_channel, parent, false);
        return new ArrayViewHolder(itemView.getRootView(), itemView.getRootView(), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrayViewHolder holder, int position) {
        holder.bind(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    class ArrayViewHolder extends RecyclerView.ViewHolder {
        private WeakReference<OnSpinerItemClick> listenerRef;

        TextView text;

        public ArrayViewHolder(@NonNull View rootView, @NonNull View itemView, @NonNull OnSpinerItemClick listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            text = itemView.findViewById(R.id.youtube_channel_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerRef.get().onClick(items.get(getAdapterPosition()), getAdapterPosition());
//                    Log.e("ViewHolderClick: ", items.get(getAdapterPosition()));
                }
            });
        }

        void bind(String string) {
            text.setText(string);
        }



    }


}
