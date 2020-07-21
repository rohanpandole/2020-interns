import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
object Test
{
  def main(args: Array[String]): Unit =
  {
    Logger.getLogger("org").setLevel(Level ERROR)
    val spa=SparkSession.builder.master("local[*]").getOrCreate()


  }

}
