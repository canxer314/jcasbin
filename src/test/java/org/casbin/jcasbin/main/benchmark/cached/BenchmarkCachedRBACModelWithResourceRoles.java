package org.casbin.jcasbin.main.benchmark.cached;

import org.casbin.jcasbin.main.CachedEnforcer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BenchmarkCachedRBACModelWithResourceRoles {
    private static CachedEnforcer e = new CachedEnforcer("examples/rbac_with_resource_roles_model.conf", "examples/rbac_with_resource_roles_policy.csv", false);

    public static void main(String args[]) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BenchmarkCachedRBACModelWithResourceRoles.class.getName())
            .exclude("Pref")
            .warmupIterations(3)
            .measurementIterations(3)
            .addProfiler(GCProfiler.class)
            .forks(1)
            .build();
        new Runner(opt).run();
    }

    @Threads(1)
    @Benchmark
    public static void benchmarkCachedRBACModelWithResourceRoles() {
        for (int i = 0; i < 1000; i++) {
            e.enforce("alice", "data1", "read");
        }
    }
}
