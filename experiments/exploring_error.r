library('ggplot2')

error <- read.delim("C:/Users/I827590/git/StreamedConvexHull/experiments/error.txt")

ggplot(error, aes(x = budget, y = relDiamDiff, colour = type)) + geom_point() + facet_wrap(~type)

ggplot(error, aes(x = budget, y = relAreaDiff, colour = type)) + geom_point() + facet_wrap(~type) + geom_smooth(method=loess)

ggplot(error, aes(x = budget, y = relDiamDiff, colour = type)) + geom_point() + facet_wrap(~type)

areaGraph <- ggplot(error, aes(x = errorModel1, y = relAreaDiff, colour = type)) + geom_point() + facet_wrap(~type) + geom_smooth(method=loess)+geom_smooth(method="lm",colour="red",fill="red",alpha=0.2)
ggsave("areaGraph.pdf", areaGraph, width=4, height=4)

diamGraph <- ggplot(error, aes(x = errorModel1, y = relDiamDiff, colour = type)) + geom_point() + facet_wrap(~type) + geom_smooth(method=loess) +geom_smooth(method="lm",colour="red",fill="red",alpha=0.2)
ggsave("diamGraph.pdf", diamGraph, width=4, height=4)

areaGraph2 <- ggplot(error, aes(x = errorModel2, y = relAreaDiff, colour = type)) + geom_point() + facet_wrap(~type) + geom_smooth(method=loess) +geom_smooth(method="lm",colour="red",fill="red",alpha=0.2)
ggsave("areaGraph.pdf", areaGraph, width=4, height=4)

diamGraph2 <- ggplot(error, aes(x = errorModel2, y = relDiamDiff, colour = type)) + geom_point() + facet_wrap(~type) + geom_smooth(method=loess)+geom_smooth(method="lm",colour="red",fill="red",alpha=0.2)
ggsave("diamGraph.pdf", diamGraph2, width=4, height=4)