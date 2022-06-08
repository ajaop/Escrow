package com.example.escrow.viewPager2Card;

public class CardItem {


    //Integers assigned to each layout
    public static final int LayoutCardOne = 0;
    public static final int LayoutCardTwo = 1;

    //Variable view type specifies which of the layout is expected
    private int mviewType;

    private int mTitleResource, mSubtitle1Resource, mSubtitle2Resource, mSubtitle3Resource, mLinkResource, mImage1Resource, mImage2Resource;

    public CardItem(int viewType, int title, int subtitle1, int subtitle2, int subtitle3, int link, int image1, int image2) {
        mTitleResource = title;
        mSubtitle1Resource = subtitle1;
        mSubtitle2Resource = subtitle2;
        mSubtitle3Resource = subtitle3;
        mLinkResource = link;
        mImage1Resource = image1;
        mImage2Resource = image2;
        mviewType = viewType;
    }



    public void setTitle(int title) { this.mTitleResource = title; }

    public int getTitle() {
        return mTitleResource;
    }

    public int getSubtitle1() { return mSubtitle1Resource; }

    public int getSubtitle2() {
        return mSubtitle2Resource;
    }

    public int getSubtitle3() {
        return mSubtitle3Resource;
    }

    public int getImage1() {
        return mImage1Resource;
    }

    public int getImage2() {
        return mImage2Resource;
    }

    public int getLink() {
        return mLinkResource;
    }


    private int mTitleResource_2, mSubtitleResource_2, mLinkResource_2;

    public CardItem(int viewType, int title_2, int  subtitle_2, int link_2 ){
        mTitleResource_2 = title_2;
        mSubtitleResource_2 = subtitle_2;
        mLinkResource_2 = link_2;
        mviewType = viewType;
    }

    public int getTitle_2() {
        return mTitleResource_2;
    }

    public int getSubtitle_2() { return mSubtitleResource_2; }

    public int getLink_2() {
        return mLinkResource_2;
    }

    public int getViewType(){return mviewType;}


}

