package org.casbin.jcasbin.main.benchmark.synced;

import org.casbin.jcasbin.main.SyncedEnforcer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BenchmarkSyncedKeyMatchModel {
    private static SyncedEnforcer e = new SyncedEnforcer("examples/keymatch_model.conf", "examples/keymatch_policy.csv", false);

    public static void main(String args[]) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BenchmarkSyncedKeyMatchModel.class.getName())
            .exclude("Pref")
            .warmupIterations(3)
            .measurementIterations(3)
            .addProfiler(GCProfiler.class)
            .forks(1)
            .build();
        new Runner(opt).run();
    }

    @Threads(Threads.MAX)
    @Benchmark
    public static void benchmarkKeyMatchModel() {
        for (int i = 0; i < 1000; i++) {
            e.enforce("alice", "/alice_data/resource1", "GET");
        }
    }
}
