FROM tensorflow_with_mask_rnn
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
CMD ["AnalyzerResource.py"]