package com.example.travel2;

public class Domestic {
    private String title;
    private String phone;
    private String address;
    private String returnAddress;

    public Domestic(String _title,String _phone,String _address,String _returnAddress){
        this.title = _title;
        this.phone = _phone;
        this.address = _address;
        this.returnAddress = _returnAddress;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getReturnAddress() {
        return returnAddress;
    }
}
