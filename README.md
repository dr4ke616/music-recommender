# music-recommender

Spark job music recommendation system


## Getting Data
The data is hosted on [here](http://www.iro.umontreal.ca/~lisa/datasets/image_and_question_data_1obj.tar.gz). The files are too large to be checked into github, so there is a script that can download the files and store them in the appropriate directory, or upload them to an S3 bucket.

To obtain the data and copy to local directory run:
```bash
$ ./get_data.sh local
```

To obtain the data and upload to s3 (make sure you are authenticated and that the bucket name exists)
```bash
$ ./get_data.sh s3 <SOME_BUCKET_NAME>
```


## Build
To build a jar file that can be submitted to a spark cluster:
```bash
sbt assembly
```

To run and test locally:
```bash
sbt run
```
