package com.example.projekat3.data.repositories

import com.example.projekat3.data.datasource.remote.NewsService
import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.news.NewsResponse
import com.example.projekat3.data.models.news.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class NewsRepositoryImpl(private val remoteDataSource: NewsService) : NewsRepository {

    private var idCounter: AtomicLong = AtomicLong(0)

    override fun fetchAll(): Observable<Resource<Unit>> {
//        return remoteDataSource
//            .fetchAll()
//            .map {
//                it.map { newsResponse ->//mapiramo u listu postova
//                    News(
//                        id = idCounter.getAndIncrement(),
//                        title = newsResponse.title,
//                        content = newsResponse.title,
//                        link = newsResponse.link,
//                        date = newsResponse.date,
//                        image = newsResponse.image
//                    )
//                }
//            }
//            .map {
//                Resource.Success(Unit)
//            }

    val gson = Gson()
    val listNewsType = object : TypeToken<List<NewsResponse>>() {}.type
    val news: List<NewsResponse> = gson.fromJson(json, listNewsType)
    val news1 = Observable.fromArray(news)
    return news1.map {
        it.map { newsResponse: NewsResponse ->
            println(newsResponse.title)
            News(
                id = idCounter.getAndIncrement(),
                title = newsResponse.title,
                content = newsResponse.title,
                link = newsResponse.link,
                date = newsResponse.date,
                image = newsResponse.image
            )
        }
    }
        .map {
            Resource.Success(Unit)
        }
    }

    val json = "[\n" +
            "  {\n" +
            "    \"title\": \"Pandemic-era checks rewired how these Americans see money: 'Stimulus changed how I think about what's possible'\",\n" +
            "    \"content\": \"Recipients of pandemic stimulus checks used funds to reduce debt, pay bills and build savings. For some, the money altered their financial psychology.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/18/stimulus-checks-rewired-how-some-americans-see-money.html\",\n" +
            "    \"date\": \"2022-06-18T13:55:49Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076524-1655323125023-gettyimages-643757808-dsc_0661.jpeg?v=1655323147&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Here are the three things the Fed has done wrong, and what it still isn't getting right\",\n" +
            "    \"content\": \"The Federal Reserve suddenly finds itself second-guessed as it tries to navigate the economy through inflation and away from recession.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/18/here-are-three-things-the-feds-done-wrong-and-whats-still-not-right.html\",\n" +
            "    \"date\": \"2022-06-18T14:08:44Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107075628-16552236432022-06-14t160500z_2137468529_rc2pru96fd2l_rtrmadp_0_usa-economy-fed.jpeg?v=1655223733&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Brex drops tens of thousands of small business customers as Silicon Valley adjusts to new reality\",\n" +
            "    \"content\": \"The move is the latest sign of a sea change occurring among start-ups as an abrupt shift in market conditions is forcing a new discipline on companies.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/brex-drops-small-business-customers-as-silicon-valley-adjusts-to-new-reality.html\",\n" +
            "    \"date\": \"2022-06-17T22:22:21Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106800177-1605879590607-gettyimages-1178654309-_sjp4697_2019100240215399.jpeg?v=1605879672&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"As SEC charges first brokerage to run afoul of new investor protection rule, here's how to find a good financial advisor\",\n" +
            "    \"content\": \"It may seem challenging to find a broker, financial advisor or financial planner you can trust. Here are some steps consumers can take to protect themselves. \",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/sec-issues-investor-protection-rule-charges-how-to-find-a-good-advisor.html\",\n" +
            "    \"date\": \"2022-06-17T19:21:36Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106190198-1577130822451gettyimages-1163568487.jpeg?v=1655486217&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Stocks making the biggest moves midday: Seagen, Moderna, Utz, Kroger and more\",\n" +
            "    \"content\": \"These are the stocks posting the largest moves in midday trading. \",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/stocks-making-the-biggest-moves-midday-seagen-moderna-utz-kroger-and-more.html\",\n" +
            "    \"date\": \"2022-06-17T20:19:51Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076389-16553165912022-06-15t145515z_2078150373_rc2dsu9cev1x_rtrmadp_0_kroger-results.jpeg?v=1655316675&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"China's Xi says trade with Russia expected to hit new records in the coming months\",\n" +
            "    \"content\": \"Chinese President Xi Jinping on Friday spoke via video at a St. Petersburg International Economic Forum plenary session opened by Russia's Putin.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/chinas-xi-says-trade-with-russia-to-hit-new-records-in-coming-months.html\",\n" +
            "    \"date\": \"2022-06-17T15:54:24Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107011174-1643968077696-gettyimages-1238176160-AFP_9Y373G.jpeg?v=1655480152&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Fed promises 'unconditional' approach to taking down inflation in report to Congress\",\n" +
            "    \"content\": \"Federal Reserve officials rolled out strong language Friday to describe their approach to inflation, promising a full-fledged effort to restore price stability.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/fed-promises-unconditional-approach-to-taking-down-inflation-in-report-to-congress.html\",\n" +
            "    \"date\": \"2022-06-17T18:23:56Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076498-16553215702022-06-15t190913z_2056571773_rc2isu9tilzo_rtrmadp_0_usa-fed.jpeg?v=1655321635&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Powell vows that the Fed is 'acutely focused' on bringing down inflation \",\n" +
            "    \"content\": \"Federal Reserve Chairman Jerome Powell on Friday reiterated the central bank's commitment to bringing down inflation.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/powell-vows-that-the-fed-is-acutely-focused-on-bringing-down-inflation-.html\",\n" +
            "    \"date\": \"2022-06-17T14:06:38Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076420-1655470620185-pow.jpg?v=1655470642&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Stocks making the biggest moves premarket: JD.com, Roku, Alibaba and more\",\n" +
            "    \"content\": \"These are the stocks posting the largest moves before the bell.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/stocks-making-the-biggest-moves-premarket-jdcom-roku-alibaba-and-more.html\",\n" +
            "    \"date\": \"2022-06-17T11:53:11Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/105501382-1539268619036gettyimages-1051882934.jpg?v=1599773339&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Dalio is right to short Europe, strategist says: 'The pain will go on for quite a while'\",\n" +
            "    \"content\": \"Billionaire investor Ray Dalio is right to have bet big against European stocks, according to Beat Wittmann, partner at Porta Advisors.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/dalio-is-right-to-short-europe-strategist-says-the-pain-will-go-on.html\",\n" +
            "    \"date\": \"2022-06-17T11:02:05Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107066109-1J7A1143.JPG?v=1653399380&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"China's property troubles have pushed one debt indicator above levels seen in the financial crisis\",\n" +
            "    \"content\": \"A measure of risk levels for Asia debt surpassed its 2009 financial crisis high, thanks to a surge in downgrades of Chinese property developers, Moody's said.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/17/moodys-china-real-estate-troubles-sent-debt-indicator-to-record-high.html\",\n" +
            "    \"date\": \"2022-06-17T06:47:39Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076728-1655364003599-gettyimages-1240706645-China_Real_Estate_Market.jpeg?v=1655435390&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"This fund may be an attractive move for investors in volatile, inflationary markets, Amplify ETFs CEO says\",\n" +
            "    \"content\": \"Investors may want to consider a special fund focused on high dividend yielding large-caps, according to a leading ETF fund manager.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/attractive-etf-for-a-volatile-market-is-divo-amplify-etfs-ceo.html\",\n" +
            "    \"date\": \"2022-06-16T23:58:42Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076264-16553069142022-06-15t152622z_828896675_rc2esu98m6v3_rtrmadp_0_usa-stocks.jpeg?v=1655306948&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Amid high inflation, 36% of employees earning \$100,000 or more say they are living paycheck to paycheck\",\n" +
            "    \"content\": \"The share of workers earning at least \$100,000 a year who report being cash-strapped is double what it was three years ago. \",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/more-high-earners-are-living-paycheck-to-paycheck.html\",\n" +
            "    \"date\": \"2022-06-17T17:25:43Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107077123-1655400703433-gettyimages-1171263842-385a2054.jpeg?v=1655400720&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"A day after Powell's assurances about the economy, markets are worried that 'the Fed breaks something'\",\n" +
            "    \"content\": \"The trouble is, the Fed's likely to get a recession anyway as data shows the economy is a far cry from stable.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/a-day-after-powells-assurances-markets-are-worried-something-breaks.html\",\n" +
            "    \"date\": \"2022-06-16T20:52:09Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076433-16553191972022-06-15t185211z_243008047_rc2isu93vn6o_rtrmadp_0_usa-fed.jpeg?v=1655319283&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Stocks making the biggest moves premarket: Jabil, Commercial Metals, Tesla and more\",\n" +
            "    \"content\": \"These are the stocks posting the largest moves before the bell.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/stocks-making-the-biggest-moves-premarket-jabil-commercial-metals-tesla-and-more.html\",\n" +
            "    \"date\": \"2022-06-16T15:45:50Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106908536-1625783674066-gettyimages-1327708863-dscf1768_2021070852821317.jpeg?v=1633950589&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Billionaire investor Orlando Bravo warns there's 'more pain to come' for the tech sector\",\n" +
            "    \"content\": \"Investors asking tech firms for a path to profitability are \\\"not going to love what they see,\\\" Bravo said.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/orlando-bravo-warns-theres-more-pain-to-come-for-the-tech-sector.html\",\n" +
            "    \"date\": \"2022-06-17T06:45:11Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076755-1655369536194-gettyimages-1239809319-BITCOIN_MIAMI_2022.jpeg?v=1655374259&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Goldman says Beijing is stepping up support for businesses — and names stocks that will get a boost\",\n" +
            "    \"content\": \"Whether it's more government spending or an interest rate cut, Goldman Sachs is expecting a range of policy support in China — and has stock picks for each policy.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/16/goldman-sachs-picks-a-stock-for-each-china-policy-change.html\",\n" +
            "    \"date\": \"2022-06-16T00:10:50Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107071349-1654502627151-gettyimages-1233078794-CHINA_BEIJING.jpeg?v=1654502955&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Dow sinks 700 points, dropping back below 30,000 to the lowest level in more than a year\",\n" +
            "    \"content\": \"Dow sinks 700 points, dropping back below 30,000 to the lowest level in more than a year.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/stock-market-futures-open-to-close-news.html\",\n" +
            "    \"date\": \"2022-06-16T20:48:54Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107077040-1655410275263-dow2dayscott.jpg?v=1655410300&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Powell says the Fed could hike rates by 0.75 percentage point again in July\",\n" +
            "    \"content\": \"Federal Reserve Chair Jerome Powell said Wednesday the central bank could raise interest rates by a similar magnitude at the next policy meeting in July.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/powell-says-the-fed-could-hike-rates-by-0point75-percentage-point-again-in-july.html\",\n" +
            "    \"date\": \"2022-06-15T22:38:40Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107062087-1652770397957-gettyimages-1240456634-US-WASHINGTON_DC-FED-BENCHMARK_INTEREST_RATE-RAISING.jpeg?v=1655245534&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Fed members predict more hikes with the benchmark rate above 3% by year-end\",\n" +
            "    \"content\": \"The Federal Reserve expects the fed funds rate to increase by another roughly 1.75 percentage points over the next four policy meetings to end the year at 3.4%.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/fed-members-predict-more-hikes-with-the-benchmark-rate-above-3percent-by-year-end.html\",\n" +
            "    \"date\": \"2022-06-15T19:13:17Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107024659-16463317772022-03-03t165129z_786251591_rc23vs9dptgr_rtrmadp_0_usa-fed-senate-powell.jpeg?v=1646331828&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Here's what changed in the new Fed statement\",\n" +
            "    \"content\": \"This is a comparison of Wednesday's Federal Open Market Committee statement with the one issued after the Fed's previous policymaking meeting on May 4.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/heres-what-changed-in-the-new-fed-statement.html\",\n" +
            "    \"date\": \"2022-06-15T18:05:25Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107075627-1655223826324-fed1.jpg?v=1655224029&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Fed hikes its benchmark interest rate by 0.75 percentage point, the biggest increase since 1994\",\n" +
            "    \"content\": \"The Federal Open Market Committee released its decision on interest rates Wednesday, with markets expecting a three-quarter point hike.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/fed-hikes-its-benchmark-interest-rate-by-three-quarters-of-a-point-the-biggest-increase-since-1994.html\",\n" +
            "    \"date\": \"2022-06-16T11:32:36Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107076557-1655325704443-gettyimages-1241331451-AFP_32CK762.jpeg?v=1655325883&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"It's a daunting time for retirees, who face the biggest inflation threat, financial advisors say\",\n" +
            "    \"content\": \"Seniors living on fixed incomes may have a tough time adjusting to rapidly rising costs for consumer goods and services.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/its-a-daunting-time-for-retirees-who-face-biggest-inflation-threat-advisors-says.html\",\n" +
            "    \"date\": \"2022-06-15T21:26:55Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106974364-1636657846469-gettyimages-652723004-midcentury2017_0156.jpeg?v=1655313390&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Sen. Warren asks bank regulator to reject TD's \$13.4 billion acquisition after customer-abuse report\",\n" +
            "    \"content\": \"In a letter sent Tuesday to the OCC, Warren cited a May 4 report that alleged that TD used tactics similar to those in the Wells Fargo fake accounts scandal.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/warren-asks-bank-regulator-to-reject-td-deal-after-customer-abuse-report.html\",\n" +
            "    \"date\": \"2022-06-15T21:21:00Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106966102-1635282271508-gettyimages-1236146867-security_Afghanistan078_102621.jpeg?v=1641388572&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Here's everything the Fed is expected to announce, including the biggest rate hike in 28 years\",\n" +
            "    \"content\": \"The Federal Reserve on Wednesday is expected to do something it hasn't done in 28 years — increase interest rates by three-quarters of a percentage point.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/heres-everything-the-fed-is-expected-to-announce-including-the-biggest-rate-hike-in-28-years.html\",\n" +
            "    \"date\": \"2022-06-15T15:34:57Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107060534-1652390912126-jerry.jpg?v=1653582018&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Stocks making the biggest moves premarket: Baidu, MicroStrategy, Moderna and more\",\n" +
            "    \"content\": \"These are the stocks posting the largest moves before the bell.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/stocks-making-the-biggest-moves-premarket-baidu-microstrategy-moderna-and-more.html\",\n" +
            "    \"date\": \"2022-06-16T15:45:03Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106820692-1610035427381-gettyimages-1230424548-US_STOCKS.jpeg?v=1610034676&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Robinhood to fall to \$5 as trading slows, main revenue source is threatened, Atlantic Equities says\",\n" +
            "    \"content\": \"A recovery for Robinhood may not be in the cards anytime soon, according to Atlantic Equities. \",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/robinhood-to-fall-to-5-as-trading-slows-main-revenue-source-is-threatened-atlantic-equities-says.html\",\n" +
            "    \"date\": \"2022-06-15T10:51:08Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/106918987-16275791572021-07-29t162622z_1786553888_rc2cuo9whxgh_rtrmadp_0_robinhood-ipo.jpeg?v=1627579181&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Bill Gates says crypto and NFTs are '100% based on greater fool theory'\",\n" +
            "    \"content\": \"The tech billionaire said he's \\\"not involved\\\" in crypto: \\\"I'm not long or short any of those things.\\\"\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/bill-gates-says-crypto-and-nfts-are-based-on-greater-fool-theory.html\",\n" +
            "    \"date\": \"2022-06-15T23:47:47Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/105894487-15571466884731u8a0054r.jpg?v=1585159939&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"China's economic numbers come in better than expected, but 'difficulties and challenges' remain\",\n" +
            "    \"content\": \"China released economic data for May that topped muted expectations for a month hampered by Covid controls.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/china-economy-may-retail-industrial-production-fixed-asset-investment.html\",\n" +
            "    \"date\": \"2022-06-15T06:55:25Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107075325-1655195606519-gettyimages-1402648937-vcg111387308646.jpeg?v=1655195759&w=1920&h=1080\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Luxury brands say China's latest Covid wave has whacked consumer demand\",\n" +
            "    \"content\": \"Luxury brands have slashed expectations for their China business this year after the country's latest Covid lockdowns, according to an Oliver Wyman survey.\",\n" +
            "    \"link\": \"https://www.cnbc.com/2022/06/15/luxury-brands-say-chinas-latest-covid-wave-whacked-consumer-demand.html\",\n" +
            "    \"date\": \"2022-06-15T04:35:50Z\",\n" +
            "    \"image\": \"https://image.cnbcfm.com/api/v1/image/107075298-1655187607572-gettyimages-1401012424-_r6_4751_1eb55b92-84b0-449b-af15-c4f97d9bbd37.jpeg?v=1655196045&w=1920&h=1080\"\n" +
            "  }\n" +
            "]"

}