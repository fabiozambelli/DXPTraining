FROM liferay/dxp:7.3.10-u5
ENV LIFERAY_WORKSPACE_ENVIRONMENT=local
COPY --chown=liferay:liferay deploy /mnt/liferay/deploy
COPY --chown=liferay:liferay patching /mnt/liferay/patching
COPY --chown=liferay:liferay scripts /mnt/liferay/scripts
COPY --chown=liferay:liferay configs /home/liferay/configs
COPY --chown=liferay:liferay 100_liferay_image_setup.sh /usr/local/liferay/scripts/pre-configure/100_liferay_image_setup.sh
COPY --chown=liferay:liferay ./configs/local/wait-for.sh /usr/local/bin/wait-for.sh
