FROM node:9.2.0-alpine 

RUN apk update && \  
    apk upgrade && \
    apk add git g++ gcc libgcc libstdc++ linux-headers make python && \
    apk update 
  
RUN git clone https://github.com/sass/sassc && cd sassc && \  
    git clone https://github.com/sass/libsass && \
    SASS_LIBSASS_PATH=/sassc/libsass make && \
    mv bin/sassc /usr/bin/sassc && \
    cd ../ && rm -rf /sassc

ENV SASS_BINARY_PATH=/usr/lib/node_modules/node-sass/build/Release/binding.node  

RUN git clone --recursive https://github.com/sass/node-sass.git && \  
    cd node-sass && \
    git submodule update --init --recursive && \
    npm install && \
    node scripts/build -f && \
    cd ../ && rm -rf node-sass

RUN touch $HOME/.npmrc && echo "sass_binary_cache=${SASS_BINARY_PATH}" >> $HOME/.npmrc
