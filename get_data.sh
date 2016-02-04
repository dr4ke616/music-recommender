#!/bin/bash

NICE_NAME="profiledata"
UGLY_NAME="profiledata_06-May-2005"
TEMP_LOCATION="/tmp/profiledata.tar.gz"
AWS=`which aws`

# Local destination
LOCAL_DATA_DESTINATION="src/main/resources"

# S3 destination
S3_BUCKET=$2

TEST_DATA_LOCATION="http://www.iro.umontreal.ca/~lisa/datasets/$UGLY_NAME.tar.gz"


handle_local() {
    echo "Getting data storing it at `pwd`/$LOCAL_DATA_DESTINATION"
    rm -rf $LOCAL_DATA_DESTINATION/$NICE_NAME
    curl $TEST_DATA_LOCATION --output $TEMP_LOCATION
    tar -xzf $TEMP_LOCATION -C $LOCAL_DATA_DESTINATION/
    mv $LOCAL_DATA_DESTINATION/$UGLY_NAME $LOCAL_DATA_DESTINATION/$NICE_NAME
    rm $TEMP_LOCATION
}


handle_s3() {
    [ "$S3_BUCKET" = "" ] && {
        echo "You need to specify a bucket name"
        exit 1
    }
    echo "Getting data and uploading to s3 bucket $S3_BUCKET"

    [ "$AWS" = "" ] && {
        echo "aws command line tool can't. Check http://docs.aws.amazon.com/cli/latest/userguide/installing.html"
        exit 1
    }

    curl $TEST_DATA_LOCATION --output $TEMP_LOCATION
    tar -xzf $TEMP_LOCATION -C /tmp/
    mv /tmp/$UGLY_NAME /tmp/$NICE_NAME
    $AWS s3 cp /tmp/$NICE_NAME s3://$S3_BUCKET/$NICE_NAME --recursive
    rm -rf /tmp/$NICE_NAME
    rm $TEMP_LOCATION
}

function echo_usage {
    echo "Get sample data script."
    echo $"Usage $0 {s3|local}"
    exit 1
}

case $1 in
    s3)
        handle_s3
        ;;
    local)
        handle_local
        ;;
    *)
        echo_usage
esac
