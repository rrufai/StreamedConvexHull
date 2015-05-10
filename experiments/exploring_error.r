library('ggplot2')
library(mgcv) 
require(methods)
error <- read.delim("C:/Users/I827590/git/StreamedConvexHull/experiments/experiment1.txt")

ggplot(error, aes(x = k, y = Area, z = Distance)) + geom_point() + geom_smooth(method=loess,colour="red",alpha=0.2)
ggplot(error, aes(x = k, y = Distance)) + geom_point() + geom_smooth(method=loess,colour="red",alpha=0.2)
x <- c(error~k)
y <- c(error~Area)
lm(y ~ poly(x, 3, raw=TRUE), error)

# Importing your data
dataset <- read.table(text='
id  k 	 DistanceError 	 Evictions 	 Goodness
1 	16 	 0.223345220177 	 9984 	Area 
2 	16 	 0.212463646966 	 9984 	Distance 
3 	16 	 0.634275294728 	 9984 	Angle 
4 	17 	 0.192608888692 	 9983 	Area 
5 	17 	 0.208698232763 	 9983 	Distance 
6 	17 	 0.597469182042 	 9983 	Angle 
7 	18 	 0.185857630202 	 9982 	Area 
8 	18 	 0.207894780785 	 9982 	Distance 
9 	18 	 0.624423204864 	 9982 	Angle 
10 	19 	 0.172777250983 	 9981 	Area 
11 	19 	 0.188804435860 	 9981 	Distance 
12 	19 	 0.645772482313 	 9981 	Angle 
13 	20 	 0.162998474598 	 9980 	Area 
14 	20 	 0.176609847122 	 9980 	Distance 
15 	20 	 0.635220226509 	 9980 	Angle 
16 	21 	 0.160885293033 	 9979 	Area 
17 	21 	 0.178524740052 	 9979 	Distance 
18 	21 	 0.617529465905 	 9979 	Angle 
19 	22 	 0.170799430396 	 9978 	Area 
20 	22 	 0.158181057349 	 9978 	Distance 
21 	22 	 0.622553074735 	 9978 	Angle 
22 	23 	 0.152065046659 	 9977 	Area 
23 	23 	 0.165777875285 	 9977 	Distance 
24 	23 	 0.547237325202 	 9977 	Angle 
25 	24 	 0.146791105829 	 9976 	Area 
26 	24 	 0.144304368621 	 9976 	Distance 
27 	24 	 0.625329227406 	 9976 	Angle 
28 	25 	 0.146618156994 	 9975 	Area 
29 	25 	 0.139928454794 	 9975 	Distance 
30 	25 	 0.547666274101 	 9975 	Angle 
31 	26 	 0.139071291897 	 9974 	Area 
32 	26 	 0.140443311304 	 9974 	Distance 
33 	26 	 0.639663861418 	 9974 	Angle 
34 	27 	 0.131928239404 	 9973 	Area 
35 	27 	 0.136450361747 	 9973 	Distance 
36 	27 	 0.558020010716 	 9973 	Angle 
37 	28 	 0.129793759572 	 9972 	Area 
38 	28 	 0.122903120569 	 9972 	Distance 
39 	28 	 0.533566727960 	 9972 	Angle 
40 	29 	 0.120699583322 	 9971 	Area 
41 	29 	 0.130844371796 	 9971 	Distance 
42 	29 	 0.598394607410 	 9971 	Angle 
43 	30 	 0.125911758059 	 9970 	Area 
44 	30 	 0.125303885134 	 9970 	Distance 
45 	30 	 0.538934699915 	 9970 	Angle 
46 	31 	 0.110758763976 	 9969 	Area 
47 	31 	 0.130111882718 	 9969 	Distance 
48 	31 	 0.521881529447 	 9969 	Angle 
49 	32 	 0.109623098145 	 9968 	Area 
50 	32 	 0.105812396705 	 9968 	Distance 
51 	32 	 0.399598046649 	 9968 	Angle 
52 	33 	 0.106996662976 	 9967 	Area 
53 	33 	 0.111255022578 	 9967 	Distance 
54 	33 	 0.513542514961 	 9967 	Angle 
55 	34 	 0.108347444719 	 9966 	Area 
56 	34 	 0.123256052223 	 9966 	Distance 
57 	34 	 0.409516045363 	 9966 	Angle 
58 	35 	 0.111026706275 	 9965 	Area 
59 	35 	 0.105478575497 	 9965 	Distance 
60 	35 	 0.498918298876 	 9965 	Angle 
61 	36 	 0.102899267133 	 9964 	Area 
62 	36 	 0.090663158116 	 9964 	Distance 
63 	36 	 0.496056697011 	 9964 	Angle 
64 	37 	 0.094346830920 	 9963 	Area 
65 	37 	 0.105402158832 	 9963 	Distance 
66 	37 	 0.400871138591 	 9963 	Angle 
67 	38 	 0.089077326130 	 9962 	Area 
68 	38 	 0.097938169807 	 9962 	Distance 
69 	38 	 0.453790423741 	 9962 	Angle 
70 	39 	 0.093893125750 	 9961 	Area 
71 	39 	 0.095867964497 	 9961 	Distance 
72 	39 	 0.432063049627 	 9961 	Angle 
73 	40 	 0.091534983208 	 9960 	Area 
74 	40 	 0.092875335707 	 9960 	Distance 
75 	40 	 0.420053295222 	 9960 	Angle 
76 	41 	 0.092980614162 	 9959 	Area 
77 	41 	 0.093545963939 	 9959 	Distance 
78 	41 	 0.442792782820 	 9959 	Angle 
79 	42 	 0.082223301447 	 9958 	Area 
80 	42 	 0.084599875737 	 9958 	Distance 
81 	42 	 0.396838941197 	 9958 	Angle 
82 	43 	 0.088017939098 	 9957 	Area 
83 	43 	 0.083542512025 	 9957 	Distance 
84 	43 	 0.449162318971 	 9957 	Angle 
85 	44 	 0.085396270818 	 9956 	Area 
86 	44 	 0.089422495127 	 9956 	Distance 
87 	44 	 0.372219987040 	 9956 	Angle 
88 	45 	 0.088301282602 	 9955 	Area 
89 	45 	 0.088160121897 	 9955 	Distance 
90 	45 	 0.341496265212 	 9955 	Angle 
91 	46 	 0.077161468257 	 9954 	Area 
92 	46 	 0.082610528000 	 9954 	Distance 
93 	46 	 0.416770942724 	 9954 	Angle  '  , header=T)

qplot(k, DistanceError, data=dataset, geom="line", shape=Goodness, color=Goodness) + geom_point()+ theme_bw()



# Importing your data
dataset <- read.table(text='
    k 	 Area_Error 	 Evictions 	 Goodness
1 	16 	 0.121763566083 	 9984 	Area 
2 	16 	 0.132006378351 	 9984 	Height 
3 	16 	 0.495574418246 	 9984 	Angle 
4 	17 	 0.107495618270 	 9983 	Area 
5 	17 	 0.116168173582 	 9983 	Height 
6 	17 	 0.509179612620 	 9983 	Angle 
7 	18 	 0.097544104235 	 9982 	Area 
8 	18 	 0.105211255955 	 9982 	Height 
9 	18 	 0.463281487941 	 9982 	Angle 
10 	19 	 0.100111698635 	 9981 	Area 
11 	19 	 0.094797922719 	 9981 	Height 
12 	19 	 0.518509916570 	 9981 	Angle 
13 	20 	 0.095756497924 	 9980 	Area 
14 	20 	 0.091484165152 	 9980 	Height 
15 	20 	 0.413791854891 	 9980 	Angle 
16 	21 	 0.091384309480 	 9979 	Area 
17 	21 	 0.091911246649 	 9979 	Height 
18 	21 	 0.560724813805 	 9979 	Angle 
19 	22 	 0.086073566937 	 9978 	Area 
20 	22 	 0.080104275667 	 9978 	Height 
21 	22 	 0.449188765288 	 9978 	Angle 
22 	23 	 0.077255485626 	 9977 	Area 
23 	23 	 0.078544981076 	 9977 	Height 
24 	23 	 0.482270672143 	 9977 	Angle 
25 	24 	 0.066490716018 	 9976 	Area 
26 	24 	 0.073416435694 	 9976 	Height 
27 	24 	 0.381854729490 	 9976 	Angle 
28 	25 	 0.063299596737 	 9975 	Area 
29 	25 	 0.073851686986 	 9975 	Height 
30 	25 	 0.414051998386 	 9975 	Angle 
31 	26 	 0.063930942430 	 9974 	Area 
32 	26 	 0.061168914956 	 9974 	Height 
33 	26 	 0.346335453880 	 9974 	Angle 
34 	27 	 0.052830408343 	 9973 	Area 
35 	27 	 0.062478635072 	 9973 	Height 
36 	27 	 0.420623000453 	 9973 	Angle 
37 	28 	 0.055819667328 	 9972 	Area 
38 	28 	 0.055570931553 	 9972 	Height 
39 	28 	 0.285742456084 	 9972 	Angle 
40 	29 	 0.056765498374 	 9971 	Area 
41 	29 	 0.056543199894 	 9971 	Height 
42 	29 	 0.310566056271 	 9971 	Angle 
43 	30 	 0.052409005381 	 9970 	Area 
44 	30 	 0.050636705966 	 9970 	Height 
45 	30 	 0.316580130976 	 9970 	Angle 
46 	31 	 0.056813224725 	 9969 	Area 
47 	31 	 0.051440779478 	 9969 	Height 
48 	31 	 0.242563253874 	 9969 	Angle 
49 	32 	 0.051283343368 	 9968 	Area 
50 	32 	 0.049391180599 	 9968 	Height 
51 	32 	 0.229883881175 	 9968 	Angle 
52 	33 	 0.041948314016 	 9967 	Area 
53 	33 	 0.050550660918 	 9967 	Height 
54 	33 	 0.299681818800 	 9967 	Angle 
55 	34 	 0.044184320284 	 9966 	Area 
56 	34 	 0.046959774200 	 9966 	Height 
57 	34 	 0.236947591974 	 9966 	Angle 
58 	35 	 0.041325214595 	 9965 	Area 
59 	35 	 0.045660730199 	 9965 	Height 
60 	35 	 0.221560684342 	 9965 	Angle 
61 	36 	 0.044220302853 	 9964 	Area 
62 	36 	 0.043492273503 	 9964 	Height 
63 	36 	 0.251946785317 	 9964 	Angle 
64 	37 	 0.041277631253 	 9963 	Area 
65 	37 	 0.041450473467 	 9963 	Height 
66 	37 	 0.215169507978 	 9963 	Angle 
67 	38 	 0.036510971368 	 9962 	Area 
68 	38 	 0.047610110157 	 9962 	Height 
69 	38 	 0.203940668758 	 9962 	Angle 
70 	39 	 0.035780076888 	 9961 	Area 
71 	39 	 0.037255427474 	 9961 	Height 
72 	39 	 0.174805593902 	 9961 	Angle 
73 	40 	 0.038949165105 	 9960 	Area 
74 	40 	 0.040905848927 	 9960 	Height 
75 	40 	 0.190493253731 	 9960 	Angle 
76 	41 	 0.036512909928 	 9959 	Area 
77 	41 	 0.033763731877 	 9959 	Height 
78 	41 	 0.180042927476 	 9959 	Angle 
79 	42 	 0.035875730565 	 9958 	Area 
80 	42 	 0.034683852649 	 9958 	Height 
81 	42 	 0.206286923075 	 9958 	Angle 
82 	43 	 0.032080713804 	 9957 	Area 
83 	43 	 0.035232706109 	 9957 	Height 
84 	43 	 0.158398603703 	 9957 	Angle 
85 	44 	 0.033170742696 	 9956 	Area 
86 	44 	 0.030007913138 	 9956 	Height 
87 	44 	 0.133556225638 	 9956 	Angle 
88 	45 	 0.032278525265 	 9955 	Area 
89 	45 	 0.034720677845 	 9955 	Height 
90 	45 	 0.160459958742 	 9955 	Angle 
91 	46 	 0.031064789677 	 9954 	Area 
92 	46 	 0.031661481878 	 9954 	Height 
93 	46 	 0.102238633891 	 9954 	Angle   '  , header=T)

qplot(k, Area_Error, data=dataset, geom="line", shape=Goodness, color=Goodness) + geom_point()+ theme_bw()


# I think one possible specification would be a cubic linear model
H <- function(e, k, n) 0.5*e / k^2
L <- function(e, k, n) 0.5*pi^2*(1/k^2 - 1/n^2) 
areads <- dataset[dataset$Type=="Area",]
y.ub <- predict(lm(Error~H(Evictions, k, 10000), data=areads))
y.lb <- predict(lm(Error~L(Evictions, k, 10000), data=areads)) # estimating the model and obtaining the fitted values from the model
qplot(k, Error, data=areads, geom="line") + geom_point() + list(geom_line(aes(x=k, y=y.lb), col=2), geom_line(aes(x=k, y=y.ub), col=4))


qplot(Error, H(Evictions, k, 10000), data=areads, geom="line") + geom_point() + list(geom_line(aes(x=k, y=y.lb), col=2), geom_line(aes(x=k, y=y.ub), col=4))

qplot(Error, Error, data=areads, geom="line") + geom_point() 

y.hat <- predict(lm(area~I(1/k^2), data=dataset)) # estimating the model and obtaining the fitted values from the model
qplot(k, error, data=dataset, geom="line", color=type) + geom_point()   + geom_line(aes(x=k, y=y.hat), col=2) # the fitted values red lines

summary(areads)
summary(y.ub)


distds <- dataset[dataset$Type=="Distance",]

mds = data.frame(k=areads$k, err=areads$Error)
str(mds)
plot(mds, pch=1, col="blue")
s<-seq(0,35, length=100)
lines(mds$k,mds$err, col="blue") # lowess line (x,y)
#lines(distds$k, distds$Error)
lines(s, 0.5*pi^2*(1/s^2-1/10000^2), lty=2, col="green")
lines(s, 0.05 + 20/s^2, lty=3, col="red")
legend( x="topright", 
        legend=c("Upper bound","Area Error", "Lower bound"), 
        col=c("red","blue","green"), lwd=1, lty=c(3,1,2), pch=c(NA_integer_,1,NA_integer_),
        merge=FALSE )




distds <- dataset[dataset$Type=="Distance",]

mds = data.frame(k=areads$k, err=areads$Error)
str(mds)
plot(mds, pch=1, col="blue")
s<-seq(0,1.2, length=1000)
plot(s,tanh(s), col="blue", type='l') # lowess line (x,y)
lines(s, s^2, col="red", lty=3)
lines(s, s^5, col="green", lty=3)

curve(x^0.2,  0.0, 1.2, col="blue", type='l', xlab=expression(score), ylab=expression(score^alpha)) # lowess line (x,y)
curve(x^0.5, 0.0, 1.2, col="cyan", lty=2, pch=2, add=TRUE)
curve(x^2, 0.0, 1.2, col="magenta", lty=3, pch=3, add=TRUE)
curve(x^3, 0.0, 1.2, col="red", lty=4, pch=4, add=TRUE)
curve(x^4, 0.0, 1.2, col="green", lty=5, pch=5, add=TRUE)
curve(x^5, 0.0, 1.2, col="black", lty=6, pch=6, add=TRUE)
curve(x^10, 0.0, 1.2,col="purple", lty=7, pch=1, add=TRUE)

legend( x="bottomright", 
        legend=c(expression(alpha == 0.2 ), expression(alpha == 0.5), expression(alpha == 2.0), expression(alpha == 3.0), expression(alpha == 4.0), expression(alpha == 5.0), expression(alpha == 10.0)), 
        col=c("blue","cyan","magenta", "red", "green", "black", "purple"), lwd=1, lty=c(1,2,3,4,5,6,7), adj=0.1)



legend( x="topright", 
        legend=c("Distance Error","Area Error"), 
        col=c("red","blue"), lwd=c(1,1), lty=c(3,1), pch=c(0,1),
        merge=FALSE )



mds = data.frame(k=areads$k, err=areads$Error)
str(mds)
plot(mds)
s<-seq(0,35, length=100)
lines(mds$k,mds$err, col="blue") # lowess line (x,y)
lines(s, 0.5*pi^2*(1/s^2-1/10000^2), lty=2, col="green")
lines(s, 0.5*(10000-s)/s^3, lty=3, col="red")
legend( x="topright", 
        legend=c("Upper bound","Area Error", "Lower bound"), 
        col=c("red","blue","green"), lwd=1, lty=c(3,1,2), 
        merge=FALSE )

mm <- nls(y~I(x^power), data=mds, start=list(power=-2), trace=T)

# Importing your data
runtime <- read.table(text='
n    k 	 time
2000   16 	5.303 
3000 	16 	6.848 
4000 	16 	7.818 
5000 	16 	8.727 
6000 	16 	11.667 
7000 	16 	11.758 
8000 	16 	13.455 
9000 	16 	16.000 
10000 	16 	25.424 
11000 	16 	18.576 
12000 	16 	19.697 
13000 	16 	21.182 
14000 	16 	25.424 
15000 	16 	23.364 
16000 	16 	27.394 
17000 	16 	29.333 
18000 	16 	29.697 
19000 	16 	31.424 
20000 	16 	33.424 
21000 	16 	35.061 
22000 	16 	35.182 
23000 	16 	37.606 
24000 	16 	41.606 
25000 	16 	46.182 
26000 	16 	49.182 
27000 	16 	47.152 
28000 	16 	47.879 
29000 	16 	49.970 
30000 	16 	52.545 
31000 	16 	51.091 
32000 	16 	53.667 
33000 	16 	54.758 
34000 	16 	62.939 
35000 	16 	63.606 
36000 	16 	74.212 
37000 	16 	63.909 
38000 	16 	79.091 
39000 	16 	68.576 
40000 	16 	71.758 
41000 	16 	76.758 
42000 	16 	75.212 
43000 	16 	81.061 
44000 	16 	79.667 
45000 	16 	87.364 
46000 	16 	80.424 
47000 	16 	97.364 
48000 	16 	82.848 
49000 	16 	87.485 
50000 	16 	90.758 '  , header=T)
mds = data.frame(n=runtime$n, time=runtime$time)
str(mds)
plot(mds)

qplot(n, time, data=runtime, geom="line") + geom_point()+geom_smooth(method=loess,colour="red",alpha=0.2)
