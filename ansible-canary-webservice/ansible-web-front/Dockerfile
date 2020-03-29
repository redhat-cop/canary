
FROM centos:7

ENV NODE_VERSION=6.12.3
ENV APP_HOME=/opt/app
ENV NODE_REPL_HISTORY=$APP_HOME/.history
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

RUN curl -o node.tar.gz https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-x64.tar.gz && \
        tar xf node.tar.gz && \
        ln -s $APP_HOME/node-v$NODE_VERSION-linux-x64/bin/node /usr/bin/node && \
        ln -s $APP_HOME/node-v$NODE_VERSION-linux-x64/bin/npm /usr/bin/npm && \
        chown 1001:0 $APP_HOME -R && \
        chmod u+g=rwx $APP_HOME -R && \
        npm config set cache $APP_HOME/.npm --global

COPY . $APP_HOME/src/

WORKDIR $APP_HOME/src/

RUN rm -rf node_modules/

RUN npm install

RUN node_modules/.bin/ng build --prod  --build-optimizer

RUN chown -R 1001:0 $APP_HOME && chmod 0775 -R $APP_HOME

USER 1001

EXPOSE 4200

CMD ["node_modules/.bin/ng", "serve", "--host", "0.0.0.0"]
