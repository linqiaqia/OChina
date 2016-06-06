package com.qiaqia.ochina.net.bean

import android.util.Log
import java.io.Serializable

/**
 * Created by 洽洽 on 2016/3/7.
 */
class  miao(): Serializable {
    val UTF8 = "UTF-8"
    val NODE_ROOT = "oschina"

    private  var notice: Notice
    private  var no:Notice?=null
init{
    Log.i("chinamiao","init");
    notice=Notice();
}
    constructor( j: Notice):this(){
        Log.i("chinamiao","user");
        foo(1)
    notice=j
}
    fun foo(va: Int): Int  {
       c(1)
        return 1
    }
    
     var c = {foo: Int ->
       Log.i("chinamiao","hahahahahahahah");
       println(foo)}
}