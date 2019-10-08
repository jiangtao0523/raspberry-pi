package com.jiangtao.util;

import com.jiangtao.client.Client;
import com.jiangtao.client.Gather;
import com.jiangtao.server.DBStore;
import com.jiangtao.server.Server;
/*
<gatehr   class="">
    <src_file></src_file>
</gather>
 */
public interface Configuration {
    Gather getGather();
    Client getClient();
    Server getServer();
    DBStore getDBStore();
    Log getLog();
    BackUp getBackUP();
}
