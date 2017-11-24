package com.guo.lab;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ironthrone on 2017/10/17 0017.
 */
@Suite.SuiteClasses({
        ExternalFileDeleteTest.class,
        ExternalCacheFileDeleteTest.class})
@RunWith(Suite.class)
public class FileDeleteTestSuite {
}
