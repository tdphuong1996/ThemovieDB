package com.appscyclone.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TDP on 26/02/2018.
 */

public class GuestSessionIdModel {
    @SerializedName("guest_session_id")
    private String guestSessionId;

    public String getGuestSessionId() {
        return guestSessionId;
    }
}
