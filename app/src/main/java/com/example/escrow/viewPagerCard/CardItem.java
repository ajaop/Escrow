package com.example.escrow.viewPagerCard;

public class CardItem {


    //Integers assigned to each layout
    public static final int LayoutCardOne = 0;
    public static final int LayoutCardTwo = 1;

    //Variable view type specifies which of the layout is expected
    private int mviewType;

    private int mTitleResource, mSubtitle1Resource, mSubtitle2Resource, mSubtitle3Resource, mLinkResource, mImage1Resource, mImage2Resource;

    public CardItem(int title, int subtitle1, int subtitle2, int subtitle3, int link, int image1, int image2, int viewType) {
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

    public void setSubtitle1(int subtitle1) { this.mSubtitle1Resource = subtitle1; }

    public int getSubtitle1() { return mSubtitle1Resource; }


    public void setSubtitle2(int subtitle2) { this.mSubtitle2Resource = subtitle2; }

    public int getSubtitle2() {
        return mSubtitle2Resource;
    }

    public void setSubtitle3(int subtitle3) { this.mSubtitle3Resource = subtitle3; }

    public int getSubtitle3() {
        return mSubtitle3Resource;
    }

    public void setImage1(int image1) { this.mImage1Resource = image1; }

    public int getImage1() {
        return mImage1Resource;
    }

    public void setImage2(int image2) { this.mImage2Resource = image2; }

    public int getImage2() {
        return mImage2Resource;
    }

    public void setLink(int link) { this.mLinkResource = link; }

    public int getLink() {
        return mLinkResource;
    }

    public int getViewType(){return mviewType;}

    /*
    private int mTitleResource_2, mSubtitleResource_2, mLinkResource_2;

    public CardItem(int title_2, int  subtitle_2, int link_2, int viewType ){
        mTitleResource_2 = title_2;
        mSubtitleResource_2 = subtitle_2;
        mLinkResource_2 = link_2;
        mviewType = viewType;
    }

    public void setTitle_2(int title_2) { this.mTitleResource_2 = title_2; }

    public int getTitle_2() {
        return mTitleResource_2;
    }

    public void setSUbtitle_2(int subtitle_2) { this.mSubtitleResource_2 = subtitle_2; }

    public int getSubtitle_2() { return mSubtitleResource_2; }

    public void setLink_2(int link_2) { this.mLinkResource_2 = link_2; }

    public int getLink_2() {
        return mLinkResource_2;
    }

    public int getViewType(){return mviewType;}

     */


}

