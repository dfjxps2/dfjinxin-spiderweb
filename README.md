# 爬虫前端应用  

## 开发历史：  
1.最先版本为jquery+java  
2.后期在重庆项目中由海南项目组同事将技术栈更新为VUE+JAVA  

## 分支说明  
**master:**  主干 `基于重庆能源项目的爬虫系统，截止版本日期为2019年10月22日。重庆能源后续开发将不在该分支进行。`  
**cqny:**  重庆能源爬虫项目分支  
**bj-dx:**  北京大兴区政府项目分支


## 本地开发过程  
### 1.下载代码
  ```
  git clone https://github.com/dfjxps2/dfjinxin-spiderweb.git  
  ```
  或  
  ```
  git clone git@github.com:dfjxps2/dfjinxin-spiderweb.git  
  ```
### 2.build项目:  
```
  cd 路径/dfjinxin-spiderweb/  
  mvn clean install -Dmaven.test.skip=true -f workbench-exception/pom.xml    
  mvn clean install -Dmaven.test.skip=true -f app-web-support/pom.xml  
  mvn clean install -Dmaven.test.skip=true -f relation-db-support/pom.xml  
  mvn clean install -Dmaven.test.skip=true -f auth-manager/pom.xml  
  mvn clean install -Dmaven.test.skip=true -f app-mvc/pom.xml  
  cd vue-shower  
  npm run build  
  ```
### 3.建库：  
  mysql中建库spiderdb  
  ```
  CREATE SCHEMA `spiderdb` DEFAULT CHARACTER SET utf8 ;
  ```
  执行 路径/dfjinxin-spiderdb/spider-sqls/spiderdb.sql  
### 4.启动项目:  
  tomcat或其他容器中启动 app-mvc模组打出来的war包  
  cd 路径/dfjinxin-spiderweb/vue-shower  
 ```
 npm run start  
 ```
