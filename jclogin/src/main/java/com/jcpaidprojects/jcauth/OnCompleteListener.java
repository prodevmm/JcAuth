package com.jcpaidprojects.jcauth;

public interface OnCompleteListener {
    void onComplete(long timestamp);
    void onError(String message);
}
