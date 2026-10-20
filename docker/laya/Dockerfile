FROM pytorch/pytorch:2.6.0-cuda12.4-cudnn9-runtime

RUN pip install --no-cache-dir laya[serve]

ENV HF_HOME=/root/.cache/huggingface

CMD ["laya-serve"]