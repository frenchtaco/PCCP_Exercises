git remote set-url --add --push origin "https://github.com/frenchtaco/PCCP_Exercises.git"
git remote add pcpp "https://github.itu.dk/PCPP/PCPP2026-Public.git"      
git pull pcpp main 


gradle "-PmainClass=exercises09.TestCountPrimesThreads" run
gradle cleanTest test --tests "exercises09.TestCountPrimesThreads"    