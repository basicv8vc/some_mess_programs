#include "server.h"

using namespace std;
using namespace homework;

typedef map<string, vector<string> >::iterator mTableFilesIterator;
typedef map<string, string>::iterator mFilesIterator;
typedef map<size_t, WorkerPtr>::iterator mWorkersIterator;
typedef map<string, vector<size_t> >::iterator mTablesIterator;


bool Server::UpdateTable(const string& tableName, const string& content)
{

    /*step 1, 先检查client的tableName是否正确/是否存在 */

    bool isNewTable = false;
    mTablesIterator it = mTables.find(tableName);

    if(it == mTables.end()){

      if(mWorkers.size() == 0)
        return false;

       /*如果tableName不存在，但是已经有workers存在，将其添加到mTablse, 并添加到各个Worker */
      else{

        isNewTable = true;
        vector<size_t> workerList;
        for(mWorkersIterator itr = mWorkers.begin(); itr != mWorkers.end(); ++itr)
          workerList.push_back(itr->first);

        mTables.insert(make_pair(tableName, workerList));
      }

    }//fi

    /*检查tablename所对应的worker列表是不是和当前workers相同：每一个表/数据 都要备份在所有的Workers */
    if(mWorkers.size() != mTables[tableName].size())
      return false;


    //server和worker分别处理(串行处理，后一阶段处理时要保证前一阶段已正确处理)，减少耦合性
    Worker::currentFileId = Worker::GetFileId(); //

    vector<size_t> workerList = mTables[tableName];
    for(vector<size_t>::iterator itr = workerList.begin(); itr != workerList.end(); ++itr){
      WorkerPtr currentWorker = mWorkers[*itr];
      map<string, vector<string> >::iterator it= currentWorker->mTableFiles.find(tableName);
      if(it == currentWorker->mTableFiles.end()){
        currentWorker->mTableFiles[tableName] = vector<string>(1, Worker::currentFileId);

      }

    }

    vector<bool> workerState; //保存每个worker返回的状态
    vector<size_t>::iterator itr = workerList.begin();


    for(; itr != workerList.end(); ++itr){
      WorkerPtr currentWorker = mWorkers[*itr];
      workerState.push_back(currentWorker->UpdateTable(tableName,content));
    }

    for(vector<bool>::iterator itr = workerState.begin(); itr != workerState.end(); ++itr){
      /*回滚时注意Server也要回滚 */
      if(! *itr){
        for(std::vector<size_t>::iterator i = workerList.begin(); i != workerList.end(); ++ i){
          WorkerPtr currentWorker = mWorkers[*i];
          currentWorker->DeleteFile(Worker::currentFileId);

          vector<string>::iterator tv = (*currentWorker).mTableFiles[tableName].begin();
          while(tv != (*currentWorker).mTableFiles[tableName].end()){
            if (*tv == Worker::currentFileId){
              (*currentWorker).mTableFiles[tableName].erase(tv);
              break;
            }
            ++tv;

          }


          if(isNewTable)
            currentWorker->mTableFiles.erase(tableName);
        }


        if(isNewTable)
          mTables.erase(tableName);

          return false;
      }//fi 回滚操作,
    } //for
    return true;

}

bool Worker::UpdateTable(const string& tableName, const string& content)
{
    map<std::string, vector<string> > mTableFilesBAK(mTableFiles);
    map<std::string, string> mFilesBAK(mFiles);

    bool flag = CreateFile(currentFileId);
    if(!flag){ //失败的原因是fileid已经存在了！所以mTableFiles, mFiles并没有变化,

        return false;
    }

    //此时mFiles已经添加了新文件,

    flag = WriteToFile(currentFileId, content);
    if(!flag){ //失败的原因是cannot find fileid, Therefore mTableFiles, mFiles never change,
        mTableFiles = mTableFilesBAK;
        mFiles = mFilesBAK;
        return false;
    }

    string contentbak = content;
    flag = ReadTable(tableName, contentbak); //contentbak经过ReadTable函数后已经变了，变成了tableName现在的fileid组成的string,
    if(!flag){ //tableName 不存在
        mTableFiles = mTableFilesBAK;
        mFiles = mFilesBAK;
        return false;
    }

    vector<string> fileids = mTableFiles[tableName];
    bool isNewTable = false;
    for(vector<string>::iterator itr = fileids.begin(); itr != fileids.end(); ++itr){
      if(*itr == currentFileId)
        isNewTable = true;
    }
    if(! isNewTable){
      fileids.push_back(currentFileId);
      mTableFiles[tableName] = fileids;
    }
    return true;

}
