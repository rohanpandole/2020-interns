import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
object Test
{
  def main(args: Array[String]): Unit =
  {
    Logger.getLogger("org").setLevel(Level ERROR)
    val spa=SparkSession.builder.master("local[*]").getOrCreate()

    import spa.implicits._
    val dataFrame = spa.read.json("/home/rohan/Spark_course_materials/SparkScala3/JsonData/demoData.json").toDF()

    dataFrame.show(false)
    dataFrame.printSchema()
    val nestedDays = dataFrame.selectExpr("rates.*")

    val dayColumn = nestedDays.columns.map(c => s"'$c', `$c`").mkString(", ")

    val filteredData = nestedDays.selectExpr(s"stack(${nestedDays.columns.length}, $dayColumn) as (`yyyy-MM-dd`, value)")
      .filter(s"`yyyy-MM-dd` between '2019-01-02' and '2019-01-04'")
      .selectExpr("`yyyy-MM-dd`", "value.*")

    filteredData.printSchema()
    filteredData.show()


  }

}
