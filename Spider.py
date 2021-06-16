# encoding:utf-8
from bs4 import BeautifulSoup  # 网页解析 获取数据
import re  # 正则表达式，文字匹配
import urllib.request, urllib.error  # 指定url获取数据的
import xlwt  # 进行excel操作的
import  mysql.connector
import uuid
def main():
    baseurl = "https://movie.douban.com/top250?start="
    # 1.爬取网页
    dataList = getData(baseurl)
    insert_Mysql(dataList)
    # # 2.逐一解析数据（边爬取边解析）
    # savePath = "豆瓣电影Top250.xls"
    # # 3.保存数据
    # saveData(savePath,dataList)

findLink = re.compile(r'<a href="(.*?)">')  # 创建正则表达式对象，表示规则（字符串的模式） #影片连接的规则
# 影片图片的连接
findImgSrc = re.compile(r'img.*src="(.*?)"', re.S)  # //re.S 忽略换行符
# 片名
findTitle = re.compile(r'<span class="title">(.+)</span>')
# 影片评分
findRating = re.compile(r'span class="rating_num" property="v:average">(.*)</span>')
# 评价人数
findJudge = re.compile(r'(\d*)人评价')
# 找到概况
findInq = re.compile(r'<span class="inq">(.*)</span>')

# 找到影片的相关内容
findBd = re.compile(r'<p class="">(.*?)</p>', re.S)


def getData(baseurl):
    dataList = []
    for i in range(0, 10):  # 调用获取信息的函数
        url = baseurl + str(i * 25)
        html = askURL(url)  # 保存获取到的网页源码
        #  print(html)
        # 解析网页~
        soup = BeautifulSoup(html, "html.parser")  # 形成树形结构对象
        for item in soup.find_all('div', class_="item"):
            # print(item) 测试查看的item
            data = []  # 保存一部电影的所有信息
            item = str(item)
            # print(item)
            # 获取影片详情连接
            link = re.findall(findLink, item)[0]  # 用正则表达式 找指定字符串
            data.append(link)
            imgSrc = re.findall(findImgSrc, item)[0]
            data.append(imgSrc)
            titles = re.findall(findTitle, item)  # 片面可能只有一个中文名 没有外文名
            # 天剑中英文名
            print(len(titles))
            if len(titles) == 2:
                ctitle = titles[0]
                print(ctitle)
                data.append(ctitle)
                otitle = titles[1].replace("/", "")  # 去掉无关的符号
                print(otitle)
                data.append(otitle)
            else:
                data.append(titles)
                data.append(' ')  # 留空
            rating = re.findall(findRating, item)[0]
            data.append(rating)

            judgeNum = re.findall(findJudge, item)[0]
            data.append(judgeNum)
            inq = re.findall(findInq, item)
            if len(inq) != 0:
                inq = inq[0].replace("。", "")
                data.append(inq)
            else:
                data.append(" ")  # 留空
            bd = re.findall(findBd, item)[0]
            bd = re.sub(r'<br(\s+)?/>(\s+)?', " ", bd)
            bd = re.sub('/', " ", bd)
            data.append(bd.strip())  # 去掉前后的空格
            # 处理好的信息放在datalist
            dataList.append(data)
    return dataList


# 得到指定一个网页的信息
def askURL(url):
    # 用户代理表示告诉服务器 我是什么类型的
    head = {
        "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36 Edg/85.0.564.68"}
    req = urllib.request.Request(url, headers=head)
    html = ""
    try:
        response = urllib.request.urlopen(req)
        html = response.read().decode("utf-8")
    except urllib.error.URLError as e:
        if hasattr(e, "code"):
            print(e, "code")
        if hasattr(e, "reason"):
            print(e, "reason")
    return html


def saveData(savePath, datalist):
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)
    sheet = book.add_sheet('doubanMoviesTop', cell_overwrite_ok=True)  # 创建工作表
    col = ('排名','电影详情连接', '图片连接', '影片中文名', '影片外文名字', '评分', '评价数', '概况', '相关信息')
    for i in range(0,col.__len__()):
        sheet.write(0, i, col[i])  # 写入列名字
    for i in range(0, datalist.__len__()):
        print("第%d调"% i)
        sheet.write(i+1,0,'Top %d'%(i+1))
        data = datalist[i]
        for j in range(0, 8):
            sheet.write(i + 1, j+1, data[j])
    book.save(savePath)  # 保存文档


def insert_Mysql(datalist):
    #连接数据库
    mydb = mysql.connector.connect(
        host="8.131.54.33",  # 数据库主机地址
        user="song",  # 数据库用户名
        passwd="song1998",  # 数据库密码
        database='movies'
    )
    #创建执行语句
    mycursor = mydb.cursor();

    #循环插入数据
    index=1
    print("开始插入............")
    for data in datalist:
        print(data)
        print(data.__len__())
        #为data产生一个uuid 方便保存在数据库中
        uuid1=uuid.uuid1()
        top ='Top'+str(index) #生成top排名
        sql="INSERT INTO movie (movie_url,movie_picture,movie_cname,movie_fname,movie_score,movie_coment_num" \
            ",movie_abstract,movie_detail,movie_id,movie_top) values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%\
            (data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],uuid1,top)

        print("插入第"+str(index))
        # #开始插入
        try:
            mycursor.execute(sql)
            mydb.commit()
            index+=1
        except:
            print("写入失败！")

    #关闭数据库连接
    mycursor.close()
    mydb.close()






if __name__ == "__main__":  # 当程序执行时
    # 调用函数
    main()
