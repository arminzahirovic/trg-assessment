FROM node:18.12.1
WORKDIR /app
COPY package.json /app/package.json
RUN npm install
COPY . /app
EXPOSE 4200
CMD ["npm", "start"]
