#!/bin/bash

NICE_NAME="profiledata"
UGLY_NAME="profiledata_06-May-2005"
TEMP_LOCATION="/tmp/profiledata.tar.gz"
DATA_DESTINATION="src/main/resources"

TEST_DATA_LOCATION="http://www.iro.umontreal.ca/~lisa/datasets/$UGLY_NAME.tar.gz"

rm -rf $DATA_DESTINATION/$NICE_NAME
curl $TEST_DATA_LOCATION --output $TEMP_LOCATION
tar -xzf $TEMP_LOCATION -C $DATA_DESTINATION/
mv $DATA_DESTINATION/$UGLY_NAME $DATA_DESTINATION/$NICE_NAME
