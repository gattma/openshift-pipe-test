FROM tensorflow/tensorflow:latest-py3

EXPOSE 5000

COPY . /app
WORKDIR /app

RUN pip install -r requirements.txt
RUN apt-get update && apt-get install -y libglib2.0-0 libsm6 libxext6 libxrender-dev git
RUN pip install git+https://github.com/waleedka/coco.git#subdirectory=PythonAPI

RUN -c chmod -x AnalyzerResource.py

ENTRYPOINT ["python"]
CMD ["AnalyzerResource.py"]