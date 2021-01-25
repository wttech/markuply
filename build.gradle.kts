import pl.allegro.tech.build.axion.release.domain.VersionConfig

plugins {
    id("pl.allegro.tech.build.axion-release")
}

group = "com.wundermanthompson.markuply"
version = scmVersion.version

configure<VersionConfig> {
    checks.aheadOfRemote = false
    checks.isSnapshotDependencies = false

    hooks.pre("fileUpdate", mutableMapOf(
            "files" to listOf("README.md"),
            "pattern" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> ">$v<" }),
            "replacement" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> ">$v<" })
    ))
    hooks.pre("fileUpdate", mutableMapOf(
            "files" to listOf("README.md"),
            "pattern" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> ":$v[\"]" }),
            "replacement" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> ":$v\"" })
    ))
    hooks.pre("fileUpdate", mutableMapOf(
            "files" to listOf("engine/src/docs/antora/antora.yml"),
            "pattern" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> "version: '$v'" }),
            "replacement" to KotlinClosure2<String, pl.allegro.tech.build.axion.release.domain.hooks.HookContext, String>({ v, _ -> "version: '$v'" })
    ))
    hooks.pre("commit")
}
