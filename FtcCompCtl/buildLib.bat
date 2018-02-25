msbuild.exe ftccomp.csproj
cd jar
FOR %%x IN (..\lib\*.dll) DO (
    ikvmstub "%%x"
)
FOR %%x IN (..\dist\*.dll) DO (
    ikvmstub -reference:..\lib\* "%%x"
)
cd ..