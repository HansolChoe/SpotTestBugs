package com.github.spotbugs;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.test.SpotBugsRule;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.hamcrest.MatcherAssert.assertThat;

public class MavenTestClassNameDetectorTest {
    @Rule
    public SpotBugsRule spotbugs = new SpotBugsRule();

    @Test
    public void testGoodCase() {
        System.out.println("test1");
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "TestClassNameGoodCase.class");
        System.out.println("test2");
        System.out.println("path="+path);
        BugCollection bugCollection = spotbugs.performAnalysis(path);
        System.out.println("test3");
        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("TEST_CLASS_NAME_NOT_DEFAULT").build();
        System.out.println("test4");
        assertThat(bugCollection, containsExactly(0, bugTypeMatcher));
    }

    @Test
    public void testBadCase() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "TeztClassNameBadCase.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("TEST_CLASS_NAME_NOT_DEFAULT").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
    }
}

/*
    @Test
    public void testGoodCase() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "GoodCase.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("MY_BUG").build();
        assertThat(bugCollection, containsExactly(0, bugTypeMatcher));
    }

    @Test
    public void testBadCase() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "BadCase.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("MY_BUG").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
    }
 */