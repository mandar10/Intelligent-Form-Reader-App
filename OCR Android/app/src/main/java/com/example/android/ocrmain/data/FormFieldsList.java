package com.example.android.ocrmain.data;

import android.provider.BaseColumns;

/**
 * Created by prathmeshmhapsekar on 30/12/17.
 */

public class FormFieldsList {

    public static final class FormFieldsEntry implements BaseColumns {
        public static final String TABLE_NAME = "collist";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_X = "Xcod";
        public static final String COLUMN_Y = "Ycod";
        public static final String COLUMN_TIMESTAMP = "Timestamp";
        public static final String COLUMN_FORMID = "formid";
    }

}
