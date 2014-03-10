#!/bin/sh

VERSION=1.0
MODULE_NAME=siren_${VERSION}-2

echo "Building Debian package for ${MODULE_NAME}"
echo

rm -rf ../target/deb-pkg
mkdir -p ../target/deb-pkg

# Add the Debian control files
cp -r debian ../target/deb-pkg
cp -r data/* ../target/deb-pkg
cp -r ../target/debian/ ../target/deb-pkg/lib

# Build the package and sign it.
cd ../target/deb-pkg
debuild --check-dirname-level 0 -b
