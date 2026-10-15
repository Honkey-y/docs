FROM openeuler/openeuler:22.03-lts

RUN sed -i '/^metalink=/d; s|http://repo.openeuler.org|https://repo.openeuler.org|g' /etc/yum.repos.d/openEuler.repo \
    && dnf --disablerepo='*' --enablerepo=OS --enablerepo=everything install -y libaio libnsl shadow util-linux tar bzip2 hostname procps-ng findutils \
    && dnf clean all \
    && groupadd -g 1070 omm \
    && useradd -u 1070 -g omm -d /home/omm -m omm

ADD openGauss-Lite-6.0.3-openEuler22.03-aarch64.tar.gz /tmp/opengauss-install/
RUN mkdir -p /usr/local/opengauss \
    && tar -xf /tmp/opengauss-install/openGauss-Lite-6.0.3-openEuler22.03-aarch64.bin -C /usr/local/opengauss \
    && cp /tmp/opengauss-install/dependency/libstdc++.so.6 /usr/local/opengauss/lib/
COPY entrypoint.sh /usr/local/bin/lab-entrypoint
COPY bootstrap.sql /opt/lab/bootstrap.sql
RUN chmod 755 /usr/local/bin/lab-entrypoint \
    && mkdir -p /var/lib/opengauss/data \
    && chown -R omm:omm /usr/local/opengauss /var/lib/opengauss /opt/lab

ENV GAUSSHOME=/usr/local/opengauss
ENV PATH=/usr/local/opengauss/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
ENV LD_LIBRARY_PATH=/usr/local/opengauss/lib
ENV PGDATA=/var/lib/opengauss/data
ENV LANG=C
EXPOSE 5432 3306
ENTRYPOINT ["/usr/local/bin/lab-entrypoint"]
