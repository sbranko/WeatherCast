package net.simplifiedcoding.navigationdrawerexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by elpatron on 11/3/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


    private Context mContext;
    private List<CityWeatherModel> cityWeatherModelList;
    private RadioButton lastChecked;
    private int lastCheckedPosition = 0;
    private int lastSelectedPosition = 0;

    MyRecyclerAdapter(Context context, List<CityWeatherModel> cityWeatherModelList) {
        this.mContext = context;
        this.cityWeatherModelList = cityWeatherModelList;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CityWeatherModel cityWeatherModel = cityWeatherModelList.get(position);




        Glide.with(mContext)
                .load(cityWeatherModel.getWeatherIcon())
                .into(holder.mWeatherIcon);



        holder.mCityName.setText(cityWeatherModel.getCityName());
        holder.mCityWeather.setText(cityWeatherModel.getCurrentWeather());
        holder.mTvCityTemp.setText(String.valueOf(Math.round(cityWeatherModel.getCurrentTemp() - 273.15)) + " °C");

        /*holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.mRadioButton.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        CityWeatherModel cityWeatherModel1 = cityWeatherModelList.get(lastCheckedPosition);
                        cityWeatherModel.setSelected(false);
                    }

                    lastChecked = holder.mRadioButton;
                }
            }
        });*/

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        if (lastSelectedPosition == position) {
            Intent intent = new Intent("MyCustomIntent");
            // add data to the Intent
          /*  intent.putExtra("message", (String) cityWeatherModelList.get(position).getCityName().toString());
            intent.putExtra("temperature", String.valueOf(Math.round(cityWeatherModelList.get(position).getCurrentTemp()-273.15)));

            intent.putExtra("picture",  cityWeatherModel.getWeatherIcon());*/

            intent.putExtra("city",cityWeatherModel);

            intent.setAction("net.simplifiedcoding.navigationdrawerexample.A_CUSTOM_INTENT");
            mContext.sendBroadcast(intent);

        }
            holder.mRadioButton.setChecked(lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return cityWeatherModelList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mWeatherIcon;
        private TextView mCityName;
        private TextView mCityWeather;
        private TextView mTvCityTemp;
        private RadioButton mRadioButton;

        MyViewHolder(View itemView) {
            super(itemView);
            mWeatherIcon = (ImageView) itemView.findViewById(R.id.iv_weather_icon);
            mCityName = (TextView) itemView.findViewById(R.id.tv_city_name);
            mCityWeather = (TextView) itemView.findViewById(R.id.tv_city_weather);
            mTvCityTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.radio_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(MyRecyclerAdapter.this.mContext,
                            "selected city is " + mCityName.getText(),
                            Toast.LENGTH_LONG).show();

                }
            });
            mRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(MyRecyclerAdapter.this.mContext,
                            "selected city is " + mCityName.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }

        public void refreshAdapter(){
            lastSelectedPosition = getAdapterPosition();
            notifyDataSetChanged();

            Toast.makeText(MyRecyclerAdapter.this.mContext,
                    "selected city is " + mCityName.getText(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==itemView.getId()){
                refreshAdapter();
            }else if (v.getId()==R.id.radio_button){
               refreshAdapter();
            }
        }
    }
}
