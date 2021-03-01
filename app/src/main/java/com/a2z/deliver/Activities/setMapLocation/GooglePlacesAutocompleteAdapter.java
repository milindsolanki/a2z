package com.a2z.deliver.activities.setMapLocation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;
        SetMapLocationView mView;
        SetMapLocationPresenter presenter;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, SetMapLocationView view) {
            super( context, textViewResourceId );
            this.mView = view;
            presenter = new SetMapLocationPresenter();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = super.getView( position, convertView, parent );
            final TextView textView = ( TextView ) row.findViewById( R.id.tvPlace );
            textView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    System.out.println( "Place:- " + textView.getText( ).toString( ) );
                    mView.onGooglePlacesClick(textView.getText().toString());
                }
            } );
            return row;
        }

        @Override
        public int getCount() {
            return resultList.size( );
        }

        @Override
        public String getItem(int index) {
            try {
                return resultList.get( index ).toString( );
            } catch (Exception e) {
                return "";
            }

        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter( ) {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults( );
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = presenter.autocomplete( constraint.toString( ) );

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size( );
                    }
                    if (filterResults != null && filterResults.count > 0) {
                        notifyDataSetChanged( );
                    } else {
                        notifyDataSetInvalidated( );
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged( );
                    } else {
                        //notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }