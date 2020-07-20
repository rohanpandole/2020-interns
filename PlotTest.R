setwd("/home/rohan/Git/2020-interns")
d1<-read.csv("/home/rohan/Git/2020-interns/processedData.csv")

 print(d1)
#day     inr     gbp
# 30 81.3510 0.87341

library(ggplot2)

d1$inr<-round(d1$inr,1)

print(d1)
#day  inr     gbp
#30 81.4 0.87341
#10 81.2 0.90423

d1$gbp<-round(d1$gbp,2)

print(d1)
#day  inr  gbp
#30 81.4 0.87

ggplot(d1,aes(day,inr,label=inr))+geom_bar(stat = "identity")+geom_text(size = 3, vjust= 2, color ="white")+theme_gray()+ggtitle("Base value of EUR is 1")+labs(x='Days of Jan 2019',y='Currency Valuation of INR')