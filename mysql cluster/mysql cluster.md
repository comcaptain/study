### Versions

To support redhat 6.5, we should use 7.6.13

Download link is [here](https://downloads.mysql.com/archives/cluster/)

### [21.2.4.1 Installing NDB Cluster on Windows from a Binary Release](https://dev.mysql.com/doc/refman/5.7/en/mysql-cluster-install-windows-binary.html)

If windows complains that "msvcr120.dll" is missing when starting up. Then you should download [Visual C++ Redistributable Packages for Visual Studio 2013](https://www.microsoft.com/en-us/download/details.aspx?id=40784)

When starting up servers, be sure to open cmd as admin

**Start up manager node:** Somehow it cannot find the "my.ini" file in current directory, and its default config directory is `C:/Program Files/MySQL/MySQL Server 5.7/mysql-cluster`. So we have to manually specify ini file location and conf directory like this: `C:/Program Files/MySQL/MySQL Server 5.7/mysql-cluster`

**Start up manager node client:** Nothing special, just run `ndb_mgm`

**Start up data node:** Run `ndbd` 

**Start up SQL node:** 

- **Initialize data directory:** Before doing that, you should execute `mysqld --initialize --console` to initialize mysql data directory. And a temp password for root user is printed on the screen like this: `[Note] A temporary password is generated for root@localhost: C-?d8%k&S:wk` 
- In manager node client, run `all status` and confirm data node is started
- Run `mysqld --console` to start up SQL node

**Connect to DB:**

- Run `mysql -u root -p`, then enter the password recorded in "initialization" step
- Run `set password = password('123')` to reset initial password
- Run `create database tony_test` to create a DB
- Run `use tony_test` to switch to `tony_test` DB