
模块实现的功能：案件和记录的选择。

详情：
1.  每条记录的item上，可以选择包含或排除当前记录。
2.  点击勾选案件，则该案件全部记录均被选中；被选中的案件和记录背景变成彩色1；
    点击勾选记录，如果某个案件中的记录被部分选中，那么仅该记录被选中，且背景变成彩色1，该案件背景变成彩色2；
                 如果某个案件的全部记录均被选中，则该案件被选中，背景变成彩色1；


输入：caseList 和 recordList
    List<CaseInfo> caseList;
    Map<Integer, List<RecordInfo>> recordList;
    在Case_MainActivity中的initData()，进行数据源的设置。
    点击item上的toggleButton按钮，修改的是recordList。（后续可将recordList保存到数据库一份）

输出：beanLists
    ArrayList<CaseRecordChooseBean> beanLists
    点击去分析中的分析，才会最终得到beanLists。（后续可将beanLists保存到数据库一份）
    平时修改包含和排除，不会修改beanLists。
    
