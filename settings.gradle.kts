rootProject.name = "CommonJudgeSystem"
include(":library")
include(":example:server")
include("example:target")
findProject(":example:target")?.name = "target"
