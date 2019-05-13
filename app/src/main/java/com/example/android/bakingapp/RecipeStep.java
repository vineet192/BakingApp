package com.example.android.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {

    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;

    public RecipeStep(String shortDescription, String description, String videoUrl) {
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoUrl = videoUrl;
    }

    public RecipeStep(Parcel parcel) {

        mShortDescription = parcel.readString();
        mDescription = parcel.readString();
        mVideoUrl = parcel.readString();
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
    }

    static final Parcelable.Creator<RecipeStep> CREATOR
            = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel parcel) {
            return new RecipeStep(parcel);
        }

        @Override
        public RecipeStep[] newArray(int i) {
            return new RecipeStep[i];
        }
    };
}
