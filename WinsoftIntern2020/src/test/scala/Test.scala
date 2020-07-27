import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
object Test
{
  def main(args: Array[String]): Unit =
  {
    Logger.getLogger("org").setLevel(Level ERROR)

    val spa=SparkSession.builder.master("local[*]").getOrCreate()

    /* val spa: SparkSession = SparkSession.builder().master("local[*]")
    .appName("TestSuite")
    //.config("spark.sql.shuffle.partitions", "2")
    .getOrCreate()*/


    import spa.implicits._
    val dataFrame = spa.read.option("multiLine", true).json("/home/rohan/Git/2020-interns/data.json")

    dataFrame.show(false)
    dataFrame.printSchema()
    val nestedDays = dataFrame.selectExpr("rates.*")

    val dayColumn = nestedDays.columns.map(c => s"'$c', `$c`").mkString(", ")

    val filteredData = nestedDays.selectExpr(s"stack(${nestedDays.columns.length}, $dayColumn) as (`yyyy-MM-dd`, value)")
      .filter(s"`yyyy-mm-dd` between '2019-01-02' and '2019-01-31'")
      .selectExpr("`yyyy-mm-dd`", "value.*")

    filteredData.printSchema()
    filteredData.show()

    filteredData.select("yyyy-mm-dd","INR","GBP").show(false)

    //filteredData.select("yyyy-mm-dd","INR","GBP").write.format("csv").save("/home/rohan/Git/2020-interns/processedJsonData")

    spa.stop()
  }
}
