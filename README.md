# music-recommender

Spark job music recommendation system


## Getting Data
The data is hosted on [here](http://www.iro.umontreal.ca/~lisa/datasets/profiledata_06-May-2005.tar.gz). The files are too large to be checked into github, so there is a script that can download the files and store them in the appropriate directory, or upload them to an S3 bucket.

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

Checkout the configuration file at `src/main/resources/application.conf`. If you are running locally you may need to tweak the `driver.memory` and `executor.memory` settings.

Finally, go to [http://localhost:4040/](http://localhost:4040/) to get access to Spark UI
