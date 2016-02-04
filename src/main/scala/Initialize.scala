import com.typesafe.config.Config
import org.apache.spark.{SparkContext, SparkConf, Logging}

object Initialize extends Logging {

  def getSparkContext(conf: Config): SparkContext = {
    val sparkConf = new SparkConf()
      .setMaster(conf.getString("spark.master"))
      .setAppName(conf.getString("spark.appname"))
      .set("spark.default.parallelism", conf.getString("spark.parallelism"))
      .set("spark.shuffle.service.enabled", "true")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.executor.extraJavaOptions", "-XX:+UseG1GC")
      .set("spark.io.compression.codec", "lz4")
      .set("spark.dynamicAllocation.enabled", "true")
      .set("spark.executor.instances", conf.getString("spark.executor.instances"))
      .set("yarn.scheduler.capacity.resource-calculator", "org.apache.hadoop.yarn.util.resource.DominantResourceCalculator")
      .set("spark.driver.memory", conf.getString("spark.driver.memory"))
      .set("spark.executor.memory", conf.getString("spark.executor.memory"))

    new SparkContext(sparkConf)
  }

}